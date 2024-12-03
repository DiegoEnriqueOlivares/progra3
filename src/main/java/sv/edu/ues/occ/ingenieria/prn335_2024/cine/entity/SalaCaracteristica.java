package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "sala_caracteristica" )
@NamedQueries({
        @NamedQuery(name = "SalaCaracteristica.finByIdSala",query = "SELECT sc FROM SalaCaracteristica sc WHERE sc.idSala.idSala=:idSala ORDER BY sc.idTipoSala.nombre ASC"),
        @NamedQuery(name = "SalaCaracteristica.countByIdSala",query = "SELECT COUNT(sc.idSalaCaracteristica)FROM SalaCaracteristica sc WHERE sc.idSala.idSala=:idSala")
})
public class SalaCaracteristica implements Serializable {
    @Id
    @Column(name = "id_sala_caracteristica", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSalaCaracteristica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_sala")
    private TipoSala idTipoSala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Sala idSala;

    @Lob
    @Column(name = "valor")
    private String valor;

    public SalaCaracteristica() {
    }
    public SalaCaracteristica(Long idSalaCaracteristica) {
        this.idSalaCaracteristica = idSalaCaracteristica;
    }

    public Long getIdSalaCaracteristica() {
        return idSalaCaracteristica;
    }

    public void setIdSalaCaracteristica(Long id) {
        this.idSalaCaracteristica = id;
    }

    public TipoSala getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(TipoSala idTipoSala) {
        this.idTipoSala = idTipoSala;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}