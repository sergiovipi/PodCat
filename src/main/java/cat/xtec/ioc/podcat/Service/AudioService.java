package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Repository.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class AudioService {

    @Autowired
    private PodcastRepository podcastRepository;

    @Value("${audio.path}")
    private String audioPath;

    public void uploadAudio(Podcast podcast, MultipartFile audioFile) throws IOException {

        // Nom de l'arxiu
        String nameAudio = audioFile.getOriginalFilename();

        // Crea un objecte "File" que representa la ruta completa on es desarà l'arxiu
        File fileAudio = new File(audioPath + nameAudio);

        // Transfereix l'arxiu pujat a la ruta corresponent
        audioFile.transferTo(fileAudio);

        // Actualitza el camp "àudio" de l'entitat "Podcast" amb el nom de l'arxiu
        podcast.setAudio(nameAudio);
    }

    public void updateAudio(Long id, MultipartFile audioFile) throws IOException {

        // Obtenir el Podcast per ID
        Optional<Podcast> optionalPodcast = podcastRepository.findById(id);

        if (optionalPodcast.isPresent()) {
            Podcast podcast = optionalPodcast.get();

            // Nom de l'arxiu
            String nameAudio = audioFile.getOriginalFilename();

            // Eliminar el fitxer d'àudio existent
            String oldAudio = podcast.getAudio();
            if (oldAudio != null) {
                File fileOld = new File(audioPath + oldAudio);
                if (fileOld.exists()) {
                    fileOld.delete();
                }
            }

            // Crea un objecte "File" que representa la ruta completa on es desarà l'arxiu
            File fileAudio = new File(audioPath + nameAudio);

            // Transfereix l'arxiu pujat a la ruta corresponent
            audioFile.transferTo(fileAudio);

            // Actualitza el camp "àudio" de l'entitat "Podcast" amb el nom de l'arxiu
            podcast.setAudio(nameAudio);

        } else {
            throw new EntityNotFoundException("No es va trobar el Podcast amb ID " + id);
        }
    }
}
