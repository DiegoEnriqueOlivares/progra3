package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "factura")
public class Factura implements Serializable {
    @Id
    @Column(name = "id_factura", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    @Size(max = 255)
    @Column(name = "cliente")
    private String cliente;

    @Size(max = 155)
    @Column(name = "dui", length = 155)
    private String dui;

    @Column(name = "fecha")
    private OffsetDateTime fecha;

    @Lob
    @Column(name = "comentarios")
    private String comentarios;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "idFactura")
    private List<FacturaDetalleProducto> facturaDetalleProductoList;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "idFactura")
    private List<FacturaDetalleSala> facturaDetalleSalaList;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "idFactura")
    private List<Pago> pagoList;

    public Factura() {
    }

    public Factura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long id) {
        this.idFactura = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public OffsetDateTime getFecha() {
        return fecha;
    }

    public void setFecha(OffsetDateTime fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

}