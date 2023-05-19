package cat.xtec.ioc.podcat.Model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "podcasts", catalog = "podcat")
public class Podcast {

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuari")
    private Usuari usuari;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_canal")
    @NotNull
    private Canal canal;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_podcast")
    private Long id;

    @Column(name = "titol", nullable = false, unique = true)
    private String titol;

    @Column(name = "descripcio", nullable = false)
    private String descripcio;

    @Column(name = "genere", nullable = false)
    private String genere;

    @Column(name = "etiquetes", nullable = false)
    private String etiquetes;

    @Column(name = "data_publicacio", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataPublicacio;

    @Column(name = "fil", nullable = true)
    private Integer fil;

    @Column(name = "imatge", nullable = false)
    private String imatge;

    @Column(name = "audio", nullable = false)
    private String audio;

    public Podcast() {
    }

    public Podcast(Usuari usuari, Canal canal, Long id, String titol, String descripcio, String genere, String etiquetes, LocalDateTime dataPublicacio, Integer fil, String imatge, String audio) {
        this.usuari = usuari;
        this.canal = canal;
        this.id = id;
        this.titol = titol;
        this.descripcio = descripcio;
        this.genere = genere;
        this.etiquetes = etiquetes;
        this.dataPublicacio = dataPublicacio;
        this.fil = fil;
        this.imatge = imatge;
        this.audio = audio;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
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

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getEtiquetes() {
        return etiquetes;
    }

    public void setEtiquetes(String etiquetes) {
        this.etiquetes = etiquetes;
    }

    public LocalDateTime getDataPublicacio() {
        return dataPublicacio;
    }

    public void setDataPublicacio(LocalDateTime dataPublicacio) {
        this.dataPublicacio = dataPublicacio;
    }

    public Integer getFil() {
        return fil;
    }

    public void setFil(Integer fil) {
        this.fil = fil;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "usuari=" + usuari +
                ", canal=" + canal +
                ", id=" + id +
                ", titol='" + titol + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", genere='" + genere + '\'' +
                ", etiquetes='" + etiquetes + '\'' +
                ", dataPublicacio=" + dataPublicacio +
                ", fil=" + fil +
                ", imatge='" + imatge + '\'' +
                ", audio='" + audio + '\'' +
                '}';
    }
}
