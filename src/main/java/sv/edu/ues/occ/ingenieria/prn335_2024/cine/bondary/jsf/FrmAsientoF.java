package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Dependent
public class FrmAsientoF extends FrmAbstractDataPersistF<Asiento> implements Serializable {

    @Inject
    AsientoBean asientoBean;
    @Inject
    FrmAsientoCaracteristicaF frmAsientoCaracteristicaF;
    Integer idSala;

    @Override
    protected String getRowKeyFromEntity(Asiento entity) {
        if (entity != null && entity.getIdAsiento() != null) {
            return entity.getIdAsiento().toString();
        }
        return null;
    }

    @Override
    protected Asiento getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return asientoBean.findById(Long.parseLong(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        return asientoBean.countSala(this.idSala);
    }

    @Override
    protected List<Asiento> loadRegistros(int desde, int max) {
        return  asientoBean.findByIdSala(this.idSala, desde, max);
    }

    @Override
    protected void createRegistro(Asiento registro) {
          asientoBean.create(registro);
    }

    @Override
    protected Asiento updateRegistro(Asiento registro) {
        return asientoBean.update(registro);
    }

    @Override
    protected void deleteRegistro(Asiento registro) {
        asientoBean.delete(registro);
    }

    @Override
    protected Asiento createNewEntity() {
        Asiento asiento = new Asiento();
        if (idSala != null) {
            // Crear una nueva instancia de Sala con el idSala existente
            Sala sala = new Sala();
            sala.setIdSala(this.idSala);// Establecer el ID de la Sala
            asiento.setIdSala(sala);// Asignar la Sala al Asiento
        }
        asiento.setActivo(true); // Establecer el asiento como activo
        return asiento;
    }

    @Override
    public Object getTituloDePagina() {
        return "Asiento";
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public void setFacesContext(FacesContext facesContext) {

    }
    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    @Override
    public LazyDataModel<Asiento> getModelo() {
        return super.getModelo();
    }

    @Override
    public void setModelo(LazyDataModel<Asiento> modelo) {
        super.setModelo(modelo);
    }

    public FrmAsientoCaracteristicaF getFrmAsientoCaracteristicaF() {
        return frmAsientoCaracteristicaF;
    }

    public void setFrmAsientoCaracteristicaF(FrmAsientoCaracteristicaF frmAsientoCaracteristicaF) {
        this.frmAsientoCaracteristicaF = frmAsientoCaracteristicaF;
    }


}

