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
public class ImageService {

    @Autowired
    private PodcastRepository podcastRepository;

    @Value("${image.path}")
    private String imagePath;

    public void uploadImage(Podcast podcast, MultipartFile imageFile) throws IOException {

        // Nom de l'arxiu
        String nameImage = imageFile.getOriginalFilename();

        // Crea un objecte "File" que representa la ruta completa on es desarà l'arxiu
        File fileImage = new File(imagePath + nameImage);

        // Transfereix l'arxiu pujat a la ruta corresponent
        imageFile.transferTo(fileImage);

        // Actualitza el camp "imatge" de l'entitat "Podcast" amb el nom de l'arxiu
        podcast.setImatge(nameImage);
    }

    public void updateImage(Long id, MultipartFile imageFile) throws IOException {

        // Obtenir el Podcast per ID
        Optional<Podcast> optionalPodcast = podcastRepository.findById(id);

        if (optionalPodcast.isPresent()) {
            Podcast podcast = optionalPodcast.get();

            // Nom de l'arxiu
            String nameImage = imageFile.getOriginalFilename();

            // Eliminar el fitxer d'imatge existent
            String oldImage = podcast.getImatge();
            if (oldImage != null) {
                File oldFile = new File(imagePath + oldImage);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            // Crea un objecte "File" que representa la ruta completa on es desarà l'arxiu
            File fileImage = new File(imagePath + nameImage);

            // Transfereix l'arxiu pujat a la ruta corresponent
            imageFile.transferTo(fileImage);

            // Actualitza el camp "imatge" de l'entitat "Podcast" amb el nom de l'arxiu
            podcast.setImatge(nameImage);

        } else {
            throw new EntityNotFoundException("No es va trobar el Podcast amb ID " + id);
        }
    }
}
