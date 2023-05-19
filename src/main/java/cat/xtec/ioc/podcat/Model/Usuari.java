package cat.xtec.ioc.podcat.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuaris", catalog = "podcat")
public class Usuari {

    @OneToMany(mappedBy = "usuari")
    @JsonIgnore
    private List<Canal> canals;

    @OneToMany(mappedBy = "usuari")
    @JsonIgnore
    private List<Podcast> podcasts;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuari")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "cognom", nullable = false)
    private String cognom;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "rol", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'usuari'")
    private String rol;

    public Usuari() {
    }

    public Usuari(List<Canal> canals, List<Podcast> podcasts, Long id, String username, String password, String nom, String cognom, String email, String rol) {
        this.canals = canals;
        this.podcasts = podcasts;
        this.id = id;
        this.username = username;
        this.password = password;
        this.nom = nom;
        this.cognom = cognom;
        this.email = email;
        this.rol = rol;
    }

    public List<Canal> getCanals() {
        return canals;
    }

    public void setCanals(List<Canal> canals) {
        this.canals = canals;
    }

    public List<Podcast> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(List<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuari{" +
                "canals=" + canals +
                ", podcasts=" + podcasts +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nom='" + nom + '\'' +
                ", cognom='" + cognom + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
