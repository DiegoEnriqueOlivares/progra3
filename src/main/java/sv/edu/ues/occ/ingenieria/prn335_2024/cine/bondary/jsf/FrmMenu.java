package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@ViewScoped
public class FrmMenu implements Serializable {

    @Inject
    FacesContext facesContext;

    static DefaultMenuModel model;

    @PostConstruct
    public static void init() {
        // Carga el ResourceBundle
        ResourceBundle bundle = ResourceBundle.getBundle("Traducciones", FacesContext.getCurrentInstance().getViewRoot().getLocale());

        model = new DefaultMenuModel();
        DefaultSubMenu tipos = DefaultSubMenu.builder()
                .label("Tipos")
                .expanded(true)
                .build();
        DefaultSubMenu cine = DefaultSubMenu.builder()
                .label("Cine")
                .expanded(true)
                .build();

        // Añade los items utilizando el ResourceBundle
        addMenuItem(tipos, bundle.getString("frmmenu.asiento"), "TipoAsientoF.jsf");
        addMenuItem(tipos, bundle.getString("frmmenu.pago"), "TipoPagoF.jsf");
        addMenuItem(tipos, bundle.getString("frmmenu.pelicula"), "TipoPeliculaF.jsf");
        addMenuItem(tipos, bundle.getString("frmmenu.producto"), "TipoProductoF.jsf");
        addMenuItem(tipos, bundle.getString("frmmenu.reserva"), "TipoReservaF.jsf");
        addMenuItem(tipos, bundle.getString("frmmenu.sala"), "TipoSalaF.jsf");

        addMenuItem(cine, bundle.getString("frmmenu.pelicula"), "PeliculaF.jsf");
        addMenuItem(cine, bundle.getString("frmmenu.sala"), "SalaF.jsf");
        addMenuItem(cine, bundle.getString("frmmenu.sucursal"), "Sucursal.jsf");
        addMenuItem(cine, bundle.getString("frmmenu.reserva"),"ReservaF.jsf" );

        model.getElements().add(tipos);
        model.getElements().add(cine);
    }


    public void navegar(String formulario) throws IOException {
        facesContext.getExternalContext().redirect(formulario);
    }

    private static void addMenuItem(DefaultSubMenu submenu, String label, String navigationUrl) {
        DefaultMenuItem item = DefaultMenuItem.builder()
                .value(label)
                .ajax(true)
                .command("#{frmMenu.navegar('" + navigationUrl + "')}")
                .build();
        submenu.getElements().add(item);
    }

    public DefaultMenuModel getModel() {
        return model;
    }
}
