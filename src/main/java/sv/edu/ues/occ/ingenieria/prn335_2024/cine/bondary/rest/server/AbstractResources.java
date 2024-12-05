package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.rest.server;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractResources<T> implements Serializable {

    public abstract AbstractDataPersistence<T> getBean();

    public abstract Integer getId(T entity);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findRange(
            @QueryParam("first") @DefaultValue("0") int firstResult,
            @QueryParam("max") @DefaultValue("50") int maxResults) {
        try {
            if (firstResult >= 0 && maxResults <= 50) {
                List<T> entities = getBean().findRange(firstResult, maxResults);
                Long total = getBean().count();
                return Response.ok(entities)
                        .header("Total-records", total)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } else {
                return Response.status(422)
                        .header("Wrong-Parameter", "First: " + firstResult + ", Max: " + maxResults)
                        .build();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("id") Long id) {
        if (id != null) {
            try {
                T entity = getBean().findById(id);
                if (entity != null) {
                    return Response.ok(entity).type(MediaType.APPLICATION_JSON).build();
                }
                return Response.status(404).header("Not-found", "ID: " + id).build();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "Invalid ID: " + id).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(T entity, @Context UriInfo uriInfo) {
        if (entity != null && getId(entity) == null) {
            try {
                getBean().create(entity);
                if (getId(entity) != null) {
                    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                    uriBuilder.path(String.valueOf(getId(entity)));
                    return Response.created(uriBuilder.build()).build();
                }
                return Response.status(500).header("Process-error", "Record could not be created").build();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "Entity cannot be null or has ID already set").build();
    }


    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") @NotNull Integer id) {
        if (id != null) {
            try {
                T entity = getBean().findById(id);
                if (entity != null) {
                    getBean().delete(entity);
                    return Response.status(204)
                            .type(MediaType.APPLICATION_JSON)
                            .build();

                }
                return Response.status(404).header("Not-Found", "ID: " + id).build();

            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "Invalid ID: " + id).build();
    }


    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Integer id,@Valid T entity) {
        if (entity != null && id != null && id.equals(getId(entity))) {
            try {
                T existingEntity = getBean().findById(id);
                if (existingEntity != null) {
                    getBean().update(entity);
                    return Response.ok(entity)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                }

                return Response.status(404)
                        .header("Not-Found", "ID: " + id).build();

            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "Invalid ID or entity mismatch").build();
    }

}
