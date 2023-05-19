package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.PodcastRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PodcastServiceTest {

    @Mock
    private PodcastRepository podcastRepository;

    @InjectMocks
    private PodcastService podcastService;

    private Usuari usuari;

    private Canal canal;

    private Podcast podcast1, podcast2, podcast3;

    private List<Canal> canals;

    private List<Podcast> podcasts;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        usuari = new Usuari();
        usuari.setId(1L);
        usuari.setNom("usuari_test");

        canal = new Canal();
        canal.setId(1L);
        canal.setUsuari(usuari);
        canal.setTitol("canal_test");
        canal.setDescripcio("descripcio_test");
        canal.setImatge("imatge_test");

        canals = new ArrayList<>();
        canals.add(canal);

        podcast1 = new Podcast();
        podcast1.setId(1L);
        podcast1.setTitol("podcast_test");
        podcast1.setDescripcio("descripcio_test");
        podcast1.setImatge("imatge_test");
        podcast1.setGenere("genere1");
        podcast1.setCanal(canal);

        podcasts = new ArrayList<>();
        podcasts.add(podcast1);

        podcast2 = new Podcast();
        podcast2.setId(1L);
        podcast2.setTitol("podcast_test");
        podcast2.setDescripcio("descripcio_test");
        podcast2.setImatge("imatge_test");
        podcast1.setGenere("genere1");
        podcast2.setCanal(canal);

        podcasts = new ArrayList<>();
        podcasts.add(podcast2);

        podcast3 = new Podcast();
        podcast3.setId(1L);
        podcast3.setTitol("podcast_test");
        podcast3.setDescripcio("descripcio_test");
        podcast3.setImatge("imatge_test");
        podcast1.setGenere("genere2");
        podcast3.setCanal(canal);

        podcasts = new ArrayList<>();
        podcasts.add(podcast3);
    }

    @Test
    void getPodcastsTest() {
        // SET UP

        // WHEN
        when(podcastRepository.findAll()).thenReturn(podcasts);

        // EXECUTE
        List<Podcast> result = podcastService.getPodcasts();

        // ASSERT
        assertEquals(podcasts, result);
    }

    @Test
    void getPodcastByIdTest() {
        // SET UP

        // WHEN
        when(podcastRepository.findById(1L)).thenReturn(Optional.of(podcasts.get(0)));

        // EXECUTE
        Optional<Podcast> result = podcastService.getPodcastById(1L);

        // ASSERT
        assertEquals(Optional.of(podcasts.get(0)), result);
    }

    @Test
    void getPodcastsByCanalTest() {
        // SET UP

        // WHEN
        when(podcastRepository.findByCanalId(1L)).thenReturn(podcasts);

        // EXECUTE
        List<Podcast> result = podcastService.getPodcastsByCanal(canal);

        // ASSERT
        assertEquals(podcasts, result);
    }

    @Test
    void getPodcastsByUsuariTest() {
        // SET UP
        Usuari usuari = new Usuari();
        usuari.setId(1L);

        // WHEN
        when(podcastRepository.findByUsuariId(1L)).thenReturn(podcasts);

        // EXECUTE
        List<Podcast> result = podcastService.getPodcastsByUsuari(usuari);

        // ASSERT
        assertEquals(podcasts, result);
    }

    @Test
    void getPodcastsByGenereTest() {
        // SET UP
        Map<String, List<Podcast>> expected = new HashMap<>();
        expected.put("genere1", Arrays.asList(podcast1, podcast2));
        expected.put("genere2", Arrays.asList(podcast3));

        // WHEN
        when(podcastRepository.findByGeneres()).thenReturn(Arrays.asList("genere1", "genere2"));
        when(podcastRepository.findByGenere("genere1")).thenReturn(Arrays.asList(podcast1, podcast2));
        when(podcastRepository.findByGenere("genere2")).thenReturn(Arrays.asList(podcast3));

        // EXECUTE
        Map<String, List<Podcast>> result = podcastService.getPodcastsByGenere();

        // ASSERT
        assertEquals(expected, result);
    }

    @Test
    void getGeneresTest() {
        // SET UP
        List<String> generes = Arrays.asList("Test1", "Test2");

        // WHEN
        when(podcastRepository.findByGeneres()).thenReturn(generes);

        // EXECUTE
        List<String> result = podcastService.getGeneres();

        // ASSERT
        assertEquals(generes, result);
    }

    @Test
    void getDataPublicacioTest() {
        // SET UP
        List<LocalDateTime> data = Arrays.asList(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        // WHEN
        when(podcastRepository.findByDataPublicacio()).thenReturn(data);

        // EXECUTE
        List<LocalDateTime> result = podcastService.getDataPublicacio();

        // ASSERT
        assertEquals(data, result);
    }

    @Test
    void addPodcastTest() {
        // SET UP


        // WHEN
        when(podcastRepository.save(podcast1)).thenReturn(podcast1);

        // EXECUTE
        Podcast result = podcastService.addPodcast(podcast1);

        // ASSERT
        assertEquals(podcast1, result);
    }

    @Test
    void updateFullPodcastByIdTest() {
        // SET UP
        Podcast podcastUpdate = new Podcast();
        BeanUtils.copyProperties(podcast1, podcastUpdate);
        podcastUpdate.setTitol("podcast_test_updated");
        podcastUpdate.setDescripcio("descripcio_test_updated");
        podcastUpdate.setImatge("imatge_test_updated");

        // WHEN
        when(podcastRepository.findById(podcast1.getId())).thenReturn(Optional.of(podcast1));
        when(podcastRepository.save(any(Podcast.class))).thenReturn(podcastUpdate);

        // EXECUTE
        Podcast result = podcastService.updateFullPodcastById(podcastUpdate, podcast1.getId());

        // ASSERT
        verify(podcastRepository, times(1)).findById(podcast1.getId());
        verify(podcastRepository, times(1)).save(any(Podcast.class));
        assertEquals(podcastUpdate.getTitol(), result.getTitol());
        assertEquals(podcastUpdate.getDescripcio(), result.getDescripcio());
        assertEquals(podcastUpdate.getImatge(), result.getImatge());
        assertEquals(podcast1.getCanal(), result.getCanal());
    }

    @Test
    void updateFieldPodcastByIdTest() {
        // SET UP
        Podcast podcastUpdate = new Podcast();
        podcastUpdate.setId(podcast1.getId());
        podcastUpdate.setTitol("podcast_test_updated");
        podcastUpdate.setDescripcio(podcast1.getDescripcio());
        podcastUpdate.setImatge(podcast1.getImatge());
        podcastUpdate.setCanal(podcast1.getCanal());

        // WHEN
        when(podcastRepository.findById(podcast1.getId())).thenReturn(Optional.of(podcast1));
        when(podcastRepository.save(any(Podcast.class))).thenReturn(podcastUpdate);

        // EXECUTE
        Podcast result = podcastService.updateFieldPodcastById(podcastUpdate, podcast1.getId());

        // ASSERT
        verify(podcastRepository, times(1)).findById(podcast1.getId());
        verify(podcastRepository, times(1)).save(any(Podcast.class));
        assertEquals(podcastUpdate.getTitol(), result.getTitol());
        assertEquals(podcast1.getDescripcio(), result.getDescripcio());
        assertEquals(podcast1.getImatge(), result.getImatge());
        assertEquals(podcast1.getCanal().getId(), result.getCanal().getId());
    }

    @Test
    void deletePodcastByIdTest() {
        // SET UP


        // WHEN
        doNothing().when(podcastRepository).deleteById(1L);

        // EXECUTE
        Boolean result = podcastService.deletePodcastById(1L);

        // ASSERT
        assertTrue(result);
    }
}
