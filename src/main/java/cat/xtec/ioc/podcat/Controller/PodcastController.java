package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Service.PodcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080, http://localhost:8080")
@RequestMapping(path = "api/v1/podcasts")
public class PodcastController {

    @Autowired
    private PodcastService podcastService;

    @GetMapping
    public List<Podcast> getPodcasts() {
        return podcastService.getPodcasts();
    }

    @GetMapping("/sortedById")
    public List<Podcast> getPodcastsSortedById() {
        return podcastService.getPodcastsByIdDesc();
    }

    @GetMapping(path = "/{id}")
    public Optional<Podcast> getPodcastById(@PathVariable("id") Long id) {
        return this.podcastService.getPodcastById(id);
    }

    @GetMapping(path = "/canal/{id}")
    public List<Podcast> getPodcastsByCanal(@PathVariable("id") Long id) {
        Canal canal = new Canal();
        canal.setId(id);
        return podcastService.getPodcastsByCanal(canal);
    }

    @GetMapping(path = "/usuari/{id}")
    public List<Podcast> getPodcastsByUsuari(@PathVariable("id") Long id) {
        Usuari usuari = new Usuari();
        usuari.setId(id);
        return podcastService.getPodcastsByUsuari(usuari);
    }

    @GetMapping(path ="/genere")
    public ResponseEntity<Map<String, List<Podcast>>> getPodcastsByGenere() {
        Map<String, List<Podcast>> podcastsByGenere = podcastService.getPodcastsByGenere();
        return ResponseEntity.ok(podcastsByGenere);
    }

    @GetMapping(path = "/generes")
    public List<String> getGeneres() {
        return this.podcastService.getGeneres();
    }

    @GetMapping(path = "/datapublicacio")
    public List<LocalDateTime> getDataPublicacio() {
        return this.podcastService.getDataPublicacio();
    }

    @GetMapping(path = "/{id}/etiquetes")
    public List<String> getEtiquetesByPodcastId(@PathVariable("id") Long id) {
        return this.podcastService.getEtiquetesById(id);
    }

    @PostMapping
    public Podcast addPodcast(@RequestBody Podcast podcast) {
        return this.podcastService.addPodcast(podcast);
    }

    @PutMapping(path = "/{id}")
    public Podcast updateFullPodcastById(@RequestBody Podcast request, @PathVariable("id") Long id) {
        return this.podcastService.updateFullPodcastById(request, id);
    }

    @PatchMapping(path = "/{id}")
    public Podcast updateFieldPodcastById(@RequestBody Podcast request, @PathVariable("id") Long id) {
        return this.podcastService.updateFieldPodcastById(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public boolean deletePodcastById(@PathVariable("id") Long id) {
        return podcastService.deletePodcastById(id);
    }


}