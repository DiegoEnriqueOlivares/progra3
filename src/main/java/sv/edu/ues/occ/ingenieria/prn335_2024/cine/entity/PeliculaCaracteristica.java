package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "pelicula_caracteristica")
@NamedQueries({
        @NamedQuery(name = "PeliculaCaracteristica.finByIdPelicula",query = "SELECT pc FROM PeliculaCaracteristica pc WHERE pc.idPelicula.idPelicula=:idPelicula ORDER BY pc.idTipoPelicula.nombre ASC"),
        @NamedQuery(name = "PeliculaCaracteristica.countByIdPelicula",query = "SELECT COUNT(pc.idPeliculaCaracteristica)FROM PeliculaCaracteristica pc WHERE pc.idPelicula.idPelicula=:idPelicula")
})

public class PeliculaCaracteristica implements Serializable {
    @Id
    @Column(name = "id_pelicula_caracteristica", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeliculaCaracteristica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_pelicula")
    private TipoPelicula idTipoPelicula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelicula")
    private Pelicula idPelicula;

    @Lob
    @Column(name = "valor")
    private String valor;

    public PeliculaCaracteristica() {
    }

    public PeliculaCaracteristica(Long idPeliculaCaracteristica) {
        this.idPeliculaCaracteristica = idPeliculaCaracteristica;
    }

    public Long getIdPeliculaCaracteristica() {
        return idPeliculaCaracteristica;
    }

    public void setIdPeliculaCaracteristica(Long id) {
        this.idPeliculaCaracteristica = id;
    }

    public TipoPelicula getIdTipoPelicula() {
        return idTipoPelicula;
    }

    public void setIdTipoPelicula(TipoPelicula idTipoPelicula) {
        this.idTipoPelicula = idTipoPelicula;
    }

    public Pelicula getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Pelicula idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}