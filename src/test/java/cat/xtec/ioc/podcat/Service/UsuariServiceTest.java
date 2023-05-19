package cat.xtec.ioc.podcat.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.UsuariRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UsuariServiceTest {

    @Mock
    private UsuariRepository usuariRepository;

    @InjectMocks
    private UsuariService usuariService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllUsuarisTest() {

        // SET UP
        Usuari usuari1 = new Usuari();
        usuari1.setId(1L);
        usuari1.setUsername("usuari1");

        Usuari usuari2 = new Usuari();
        usuari2.setId(2L);
        usuari2.setUsername("usuari2");

        List<Usuari> llistaUsuaris = new ArrayList<>();
        llistaUsuaris.add(usuari1);
        llistaUsuaris.add(usuari2);

        // WHEN
        when(usuariRepository.findAll()).thenReturn(llistaUsuaris);

        // EXECUTE
        List<Usuari> result = usuariService.getAllUsuaris();

        // ASSERT
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("usuari1");
        assertThat(result.get(1).getUsername()).isEqualTo("usuari2");
    }

    @Test
    public void getUsuariByIdTest() {

        // SET UP
        Usuari usuari = new Usuari();
        usuari.setId(1L);
        usuari.setUsername("usuari1");

        // WHEN
        when(usuariRepository.findById(1L)).thenReturn(Optional.of(usuari));

        // EXECUTE
        Optional<Usuari> result = usuariService.getUsuariById(1L);

        // ASSERT
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getUsername()).isEqualTo("usuari1");
    }

    @Test
    public void getUserByUsernameAndPasswordTest() {

        // SET UP
        Usuari usuari = new Usuari();
        usuari.setUsername("usuari1");
        usuari.setPassword("12345");

        // WHEN
        Mockito.when(usuariRepository.findByUsernameAndPassword("usuari1", "12345")).thenReturn(usuari);

        // EXECUTE
        Usuari result = usuariService.getUserByUsernameAndPassword("usuari1", "12345");

        // ASSERT
        assertEquals(usuari.getUsername(), result.getUsername());
        assertEquals(usuari.getPassword(), result.getPassword());
    }

    @Test
    public void addUsuariTest() {

        // SET UP
        Usuari usuari = new Usuari();
        usuari.setId(1L);
        usuari.setUsername("usuari1");

        // WHEN
        when(usuariRepository.save(any(Usuari.class))).thenReturn(usuari);

        // EXECUTE
        Usuari result = usuariService.addUsuari(usuari);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("usuari1");
    }

    @Test
    public void updateFullUsuariByIdTest() {

        // SET UP
        Usuari request = new Usuari();
        request.setId(1L);
        request.setUsername("usuari1");
        request.setPassword("12345");
        request.setNom("Test Nom");
        request.setCognom("Test Cognom");
        request.setEmail("test@test.com");
        request.setRol("usuari");

        Usuari usuari = new Usuari();
        usuari.setId(1L);
        usuari.setUsername("usuari1New");
        usuari.setPassword("1234567890");
        usuari.setNom("Test Nom NEW");
        usuari.setCognom("Test Cognom NEW");
        usuari.setEmail("test_new@test.com");
        usuari.setRol("admin");

        // WHEN
        when(usuariRepository.findById(1L)).thenReturn(Optional.of(usuari));
        when(usuariRepository.save(any(Usuari.class))).thenReturn(request);

        // EXECUTE
        Usuari result = usuariService.updateFullUsuariById(request, 1L);

        // ASSERT
        assertEquals(request, result);
    }

    @Test
    public void updateUsuariFieldByIdTest() {

        // SET UP
        Usuari request = new Usuari();
        request.setUsername("usuari1");
        request.setNom("Test Nom");
        request.setCognom("Test Cognom");
        request.setEmail("test@test.com");

        Usuari usuari = new Usuari();
        usuari.setId(1L);
        usuari.setUsername("usuari1");
        usuari.setNom("Test Nom NEW");
        usuari.setCognom("Test Cognom");
        usuari.setEmail("test_new@test.com");

        // WHEN
        when(usuariRepository.findById(1L)).thenReturn(Optional.of(usuari));
        when(usuariRepository.save(any(Usuari.class))).thenReturn(request);

        // EXECUTE
        Usuari result = usuariService.updateUsuariFieldById(request, 1L);

        // ASSERT
        assertEquals(request.getUsername(), result.getUsername());
        assertEquals(request.getNom(), result.getNom());
        assertEquals(request.getCognom(), result.getCognom());
        assertEquals(request.getEmail(), result.getEmail());
    }

    @Test
    public void deleteUsuariByIdTest() {

        // SET UP
        Long idToDelete = 1L;

        // WHEN
        doNothing().when(usuariRepository).deleteById(idToDelete);

        // EXECUTE
        Boolean result = usuariService.deleteUsuariById(idToDelete);

        // ASSERT
        assertTrue(result);
    }
}

