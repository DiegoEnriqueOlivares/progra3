package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FrmTipoProductoF extends FrmAbstractDataPersistF<TipoProducto> implements Serializable {

    @Inject
    TipoProductoBean tprBean;

    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializar();
    }

    @Override
    protected String getRowKeyFromEntity(TipoProducto entity) {
        if (entity != null && entity.getIdTipoProducto() != null) {
            return entity.getIdTipoProducto().toString();
        }
        return null;
    }

    @Override
    protected TipoProducto getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return tprBean.findById(Integer.parseInt(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        return tprBean.count().intValue();
    }

    @Override
    protected List<TipoProducto> loadRegistros(int desde, int max) {
        return tprBean.findRange(desde, max);
    }

    @Override
    protected void createRegistro(TipoProducto registro) {
        tprBean.create(registro);
    }

    @Override
    protected TipoProducto updateRegistro(TipoProducto registro) {
        return tprBean.update(registro);
    }

    @Override
    protected void deleteRegistro(TipoProducto registro) {
        tprBean.delete(registro);
    }

    @Override
    public Object getTituloDePagina() {
        return "Tipo de Producto";
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public void setFacesContext(FacesContext facesContext) {

    }

    @Override
    protected TipoProducto createNewEntity() {
        TipoProducto nuevoProducto = new TipoProducto();
        nuevoProducto.setActivo(true);
        return nuevoProducto;
    }
}
