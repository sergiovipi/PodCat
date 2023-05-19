package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.CanalRepository;
import cat.xtec.ioc.podcat.Repository.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CanalService {

    @Autowired
    private CanalRepository canalRepository;

    @Autowired
    private PodcastRepository podcastRepository;

    public List<Canal> getCanals() {
        return canalRepository.findAll();
    }

    public Optional<Canal> getCanalById(Long id) {
        return canalRepository.findById(id);
    }

    public List<Canal> getCanalsByUsuari(Usuari usuari) {
        return canalRepository.findByUsuariId(usuari.getId());
    }

    public List<Podcast> getPodcastsByCanal(Long id) {
        Canal canal = canalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Canal no trobat amb aquesta id " + id));
        return podcastRepository.findByCanalId(canal.getId());
    }

    public Canal addCanal(Canal canal) {
        return canalRepository.save(canal);
    }

    public Canal updateFullCanalById(Canal request, Long id) {

        Canal canal = canalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Canal no trobat"));

        canal.setUsuari(request.getUsuari());
        canal.setId(request.getId());

        canal.setTitol(request.getTitol());
        canal.setDescripcio(request.getDescripcio());
        canal.setImatge(request.getImatge());

        return canalRepository.save(canal);
    }

    public Canal updateFieldCanalById(Canal request, Long id) {

        Canal canal = canalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Canal no trobat"));

        canal.setTitol(request.getTitol());
        canal.setDescripcio(request.getDescripcio());
        canal.setImatge(request.getImatge());

        return canalRepository.save(canal);
    }


    public Boolean deleteCanalById(Long id) {
        try {
            canalRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Filtre.html
    public List<Canal> listAll(String keyword){
        if(keyword !=null){
            return canalRepository.search(keyword);
        }else
            return (List<Canal>)canalRepository.findAll();
    }
}
