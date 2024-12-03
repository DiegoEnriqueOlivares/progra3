package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "asiento",schema = "public")
@NamedQueries({
        @NamedQuery(name = "Asiento.findByIdSala", query = "SELECT a FROM Asiento a WHERE a.idSala.idSala= :idSala ORDER BY a.idAsiento ASC"),
        @NamedQuery(name = "Asiento.countByIdSala",query = "SELECT COUNT(a.idSala)FROM Asiento a WHERE a.idSala.idSala=:idSala")
})
public class Asiento implements Serializable {
    @Id
    @Column(name = "id_asiento", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Sala idSala;

    @NotBlank(message = "Debe ingresar un nombre valido")
    @Size(max = 155, min =5, message = "Debe agregar entre 5 y 155 caracteres")
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "idAsiento")
    private List<AsientoCaracteristica> asientoCaracteristicaList;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "idAsiento")
    private List<ReservaDetalle> reservaDetalleList;

    public Asiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public Asiento() {
    }

    public Long getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Long id) {
        this.idAsiento = id;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<ReservaDetalle> getReservaDetalleList() {
        return reservaDetalleList;
    }

    public void setReservaDetalleList(List<ReservaDetalle> reservaDetalleList) {
        this.reservaDetalleList = reservaDetalleList;
    }

    public List<AsientoCaracteristica> getAsientoCaracteristicaList() {
        return asientoCaracteristicaList;
    }

    public void setAsientoCaracteristicaList(List<AsientoCaracteristica> asientoCaracteristicaList) {
        this.asientoCaracteristicaList = asientoCaracteristicaList;
    }

}