package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class PodcastService {

    @Autowired
    private PodcastRepository podcastRepository;

    public List<Podcast> getPodcasts() {
        return podcastRepository.findAll();
    }

    public List<Podcast> getPodcastsByIdDesc(){
        return podcastRepository.findAllByOrderByIdDesc();
    }

    public Optional<Podcast> getPodcastById(Long id) {
        return podcastRepository.findById(id);
    }

    public List<Podcast> getPodcastsByCanal(Canal canal) {
        return podcastRepository.findByCanalId(canal.getId());
    }

    public List<Podcast> getPodcastsByUsuari(Usuari usuari) {
        return podcastRepository.findByUsuariId(usuari.getId());
    }

    public Map<String, List<Podcast>> getPodcastsByGenere() {
        Map<String, List<Podcast>> result = new HashMap<>();
        List<String> generes = podcastRepository.findByGeneres();
        for (String genere : generes) {
            List<Podcast> podcasts = podcastRepository.findByGenere(genere);
            result.put(genere, podcasts);
        }
        return result;
    }

    public List<String> getGeneres() {
        return podcastRepository.findByGeneres();
    }

    public List<LocalDateTime> getDataPublicacio() {
        return podcastRepository.findByDataPublicacio();
    }

    public List<String> getEtiquetesById(Long id) {
        Podcast podcast = podcastRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Podcast no trobat"));

        String etiquetes = podcast.getEtiquetes();

        return Arrays.asList(etiquetes.split("\\s*,\\s*"));
    }

    public Podcast addPodcast(Podcast podcast) {
        return podcastRepository.save(podcast);
    }

    public Podcast updateFullPodcastById(Podcast request, Long id) {

        Podcast podcast = podcastRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Podcast no trobat"));

        podcast.setUsuari(request.getUsuari());
        podcast.setCanal(request.getCanal());
        podcast.setId(request.getId());

        podcast.setTitol(request.getTitol());
        podcast.setDescripcio(request.getDescripcio());
        podcast.setGenere(request.getGenere());
        podcast.setEtiquetes(request.getEtiquetes());
        podcast.setDataPublicacio(request.getDataPublicacio());
        podcast.setFil(request.getFil());
        podcast.setImatge(request.getImatge());
        podcast.setAudio(request.getAudio());

        return podcastRepository.save(podcast);
    }

    public Podcast updateFieldPodcastById(Podcast request, Long id) {

        Podcast podcast = podcastRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Podcast no trobat"));

        podcast.setTitol(request.getTitol());
        podcast.setDescripcio(request.getDescripcio());
        podcast.setGenere(request.getGenere());
        podcast.setEtiquetes(request.getEtiquetes());
        podcast.setDataPublicacio(request.getDataPublicacio());
        podcast.setFil(request.getFil());
        podcast.setImatge(request.getImatge());
        podcast.setAudio(request.getAudio());

        return podcastRepository.save(podcast);
    }

    public Boolean deletePodcastById(Long id) {
        try {
            podcastRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Filtre.html
    public List<Podcast> listAll(String keyword){
        if(keyword != null){
            return podcastRepository.searchByCanalTitol(keyword);
        } else {
            return (List<Podcast>) podcastRepository.findAll();
        }
    }
}
