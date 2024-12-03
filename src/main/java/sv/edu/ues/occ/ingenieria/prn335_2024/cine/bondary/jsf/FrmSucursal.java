package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmSucursal extends FrmAbstractDataPersistF<Sucursal> implements Serializable {
    @Inject
    SucursalBean sBean;




    @Override
    protected String getRowKeyFromEntity(Sucursal entity) {
        if (entity != null && entity.getIdSucursal() != null) {
            return entity.getIdSucursal().toString();
        }
        return null;
    }

    @Override
    protected Sucursal getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return sBean.findById(Integer.parseInt(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        return sBean.count().intValue();
    }

    @Override
    public List<Sucursal> loadRegistros(int desde, int max) {
        return sBean.findRange(desde, max);
    }

    @Override
    protected void createRegistro(Sucursal registro) {
        sBean.create(registro);
    }

    @Override
    protected Sucursal updateRegistro(Sucursal registro) {
        return sBean.update(registro);
    }

    @Override
    protected void deleteRegistro(Sucursal registro) {
        sBean.delete(registro);
    }

    @Override
    public String getTituloDePagina() {
        return "Sucursal";
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public void setFacesContext(FacesContext facesContext) {

    }

    @Override
    protected Sucursal createNewEntity() {
        Sucursal sucursal=new Sucursal();
        sucursal.setActivo(true);
        return sucursal;
    }




}
