package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.CanalRepository;
import cat.xtec.ioc.podcat.Repository.PodcastRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CanalServiceTest {

    @Mock
    private CanalRepository canalRepository;

    @Mock
    private PodcastRepository podcastRepository;

    @InjectMocks
    private CanalService canalService;

    private Usuari usuari;

    private Canal canal;

    private List<Canal> canals;

    private List<Podcast> podcasts;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setUp() {
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

        Podcast podcast = new Podcast();
        podcast.setId(1L);
        podcast.setTitol("podcast_test");
        podcast.setDescripcio("descripcio_test");
        podcast.setImatge("imatge_test");
        podcast.setCanal(canal);

        podcasts = new ArrayList<>();
        podcasts.add(podcast);
    }

    @Test
    public void getAllCanalsTest() {
        // SET UP

        // WHEN
        when(canalRepository.findAll()).thenReturn(canals);

        // EXECUTE
        List<Canal> result = canalService.getCanals();

        // ASSERT
        assertEquals(canals, result);
    }

    @Test
    public void getCanalByIdTest() {
        // SET UP

        // WHEN
        when(canalRepository.findById(1L)).thenReturn(Optional.of(canal));

        // EXECUTE
        Optional<Canal> result = canalService.getCanalById(1L);

        // ASSERT
        assertEquals(Optional.of(canal), result);
    }

    @Test
    public void getCanalsByUsuariTest() {
        // SET UP

        // WHEN
        when(canalRepository.findByUsuariId(1L)).thenReturn(canals);

        // EXECUTE
        List<Canal> result = canalService.getCanalsByUsuari(usuari);

        // ASSERT
        assertEquals(canals, result);
    }

    @Test
    public void getPodcastsByCanalTest() {
        // SET UP

        // WHEN
        when(canalRepository.findById(1L)).thenReturn(Optional.of(canal));
        when(podcastRepository.findByCanalId(1L)).thenReturn(podcasts);

        // EXECUTE
        List<Podcast> result = canalService.getPodcastsByCanal(1L);

        // ASSERT
        assertEquals(podcasts, result);
    }

    @Test
    public void addCanalTest() {
        // SET UP

        // WHEN
        when(canalRepository.save(canal)).thenReturn(canal);

        // EXECUTE
        Canal result = canalService.addCanal(canal);

        // ASSERT
        assertEquals(canal, result);
    }

    @Test
    public void updateFullCanalByIdTest() {
        // SET UP
        Canal canalUpdate = new Canal();
        BeanUtils.copyProperties(canal, canalUpdate);
        canalUpdate.setTitol("canal_test_updated");
        canalUpdate.setDescripcio("descripcio_test_updated");

        // WHEN
        when(canalRepository.findById(canal.getId())).thenReturn(Optional.of(canal));
        when(canalRepository.save(any(Canal.class))).thenReturn(canalUpdate);

        // EXECUTE
        Canal result = canalService.updateFullCanalById(canalUpdate, canal.getId());

        // ASSERT
        verify(canalRepository, times(1)).findById(canal.getId());
        verify(canalRepository, times(1)).save(any(Canal.class));
        assertEquals(canalUpdate.getTitol(), result.getTitol());
        assertEquals(canalUpdate.getDescripcio(), result.getDescripcio());
        assertEquals(canal.getImatge(), result.getImatge());
    }

    @Test
    public void updateFieldCanalByIdTest() {
        // SET UP
        Canal canalUpdate = new Canal();
        BeanUtils.copyProperties(canal, canalUpdate);
        canalUpdate.setTitol("canal_test_updated");

        // WHEN
        when(canalRepository.findById(canal.getId())).thenReturn(Optional.of(canal));
        when(canalRepository.save(any(Canal.class))).thenReturn(canalUpdate);

        // EXECUTE
        Canal result = canalService.updateFieldCanalById(canalUpdate, canal.getId());

        // ASSERT
        verify(canalRepository, times(1)).findById(canal.getId());
        verify(canalRepository, times(1)).save(any(Canal.class));
        assertEquals(canalUpdate.getTitol(), result.getTitol());
        assertEquals(canal.getDescripcio(), result.getDescripcio());
        assertEquals(canal.getImatge(), result.getImatge());
    }

    @Test
    public void deleteCanalByIdTest() {
        // SET UP

        // WHEN
        doNothing().when(canalRepository).deleteById(1L);

        // EXECUTE
        Boolean result = canalService.deleteCanalById(1L);

        // ASSERT
        assertTrue(result);
    }
}
