package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tipo_asiento", schema= "public")
public class TipoAsiento implements Serializable {
    @Id
    @Column(name = "id_tipo_asiento", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoAsiento;

    @NotBlank(message = "Debe ingresar un nombre valido")
    @Size(max = 155, min =4, message = "Debe agregar entre 4 y 155 caracteres")
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @Lob
    @Column(name = "comentarios")
    private String comentarios;

    @Lob
    @Column(name = "expresion_regular")
    private String expresionRegular;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "idTipoAsiento")
    private List<AsientoCaracteristica> asientoCaracteristicaList;

    public TipoAsiento() {
    }

    public TipoAsiento(Integer idTipoAsiento) {
        this.idTipoAsiento = idTipoAsiento;
    }

    public Integer getIdTipoAsiento() {
        return idTipoAsiento;
    }

    public void setIdTipoAsiento(Integer id) {
        this.idTipoAsiento = id;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

}