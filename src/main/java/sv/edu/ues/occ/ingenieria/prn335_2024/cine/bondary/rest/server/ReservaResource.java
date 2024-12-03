package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.rest.server;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Max;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Reserva;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("reserva")
public class ReservaResource implements Serializable {

    @Inject
    ReservaBean rBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findRange(
            @QueryParam("first") @DefaultValue("0")
            int firstResult,
            @QueryParam("max") @DefaultValue("50")
            @Max(50L)
            int maxResult) {
        try {
            if (firstResult >= 0L && maxResult <= 50L) {
                List<Reserva> encontrados = rBean.findRange(firstResult, maxResult);
                Long total = rBean.count();
                Response.ResponseBuilder builder = Response.ok(encontrados)
                        .header("Total-Records: ", total)
                        .type(MediaType.APPLICATION_JSON);
                return builder.build();
            } else {
                return Response.status(422)
                        .header("Wrong-Parameter", "first: "+firstResult+" - max: "+maxResult)
                        .build();
            }

        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity(e.getMessage()).build();
        }

    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("id") Integer id) {
        if (id != null){
            try {
                Reserva encontrado = rBean.findById(id);
                if (encontrado!=null){
                    Response.ResponseBuilder builder = Response.ok(encontrado).type(MediaType.APPLICATION_JSON);
                    return builder.build();
                }
                return Response.status(404).header("Not-Found", "id: "+id).build();
            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "id: "+id).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(Reserva reserva, @Context UriInfo uriInfo) {
        if (reserva!=null && reserva.getIdReserva()==null){
            try {
                rBean.create(reserva);
                if (reserva.getIdReserva()!=null){
                    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                    uriBuilder.path(String.valueOf(reserva.getIdReserva()));
                    return Response.created(uriBuilder.build()).build();
                }

                return Response.status(500).header("Process_Error", "Record couldnt be create").build();

            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "tiposala: " + reserva).build();
    }

    // Método PUT para actualizar una reserva
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, Reserva reserva) {
        if (id != null && reserva != null && reserva.getIdReserva() != null) {
            try {
                if (!id.equals(reserva.getIdReserva())) {
                    return Response.status(400).entity("ID en la URL no coincide con el ID de la entidad").build();
                }
                Reserva updatedReserva = rBean.update(reserva);
                return Response.ok(updatedReserva).type(MediaType.APPLICATION_JSON).build();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "id: " + id).build();
    }


    // Método DELETE para eliminar una reserva
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id) {
        if (id != null) {
            try {
                Reserva reserva = rBean.findById(id);
                if (reserva != null) {
                    rBean.delete(reserva);
                    return Response.status(204).build(); // No content
                }
                return Response.status(404).header("Not-Found", "id: " + id).build();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "id: " + id).build();
    }
}
