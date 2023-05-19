package cat.xtec.ioc.podcat.Model;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "canals", catalog = "podcat")
public class Canal {

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuari")
    @NotNull
    private Usuari usuari;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_canal")
    private Long id;

    @Column(name = "titol", nullable = false, unique = true)
    private String titol;

    @Column(name = "descripcio")
    private String descripcio;

    @Column(name = "imatge")
    private String imatge;

    public Canal() {
    }

    public Canal(Usuari usuari, Long id, String titol, String descripcio, String imatge) {
        this.usuari = usuari;
        this.id = id;
        this.titol = titol;
        this.descripcio = descripcio;
        this.imatge = imatge;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    @Override
    public String toString() {
        return "Canal{" +
                "usuari=" + usuari +
                ", id=" + id +
                ", titol='" + titol + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", imatge='" + imatge + '\'' +
                '}';
    }
}
