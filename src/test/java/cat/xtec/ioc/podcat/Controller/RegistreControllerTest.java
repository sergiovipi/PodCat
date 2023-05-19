package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.UsuariRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistreControllerTest {

    @Mock
    private UsuariRepository usuariRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistreController registreController;

    @Test
    public void registreUsuariTest() {

        // SET UP
        String username = "testuser";
        String nom = "Test";
        String cognom = "User";
        String email = "testuser@test.com";
        String password = "password123";

        // WHEN
        when(passwordEncoder.encode(password)).thenReturn(password);

        ModelAndView modelAndView = registreController.registreUsuari(username, nom, cognom, email, password);

        // VERIFY - EXECUTE
        ArgumentCaptor<Usuari> captor = ArgumentCaptor.forClass(Usuari.class);
        verify(usuariRepository).save(captor.capture());
        Usuari savedUsuari = captor.getValue();

        // ASSERT
        assertEquals(username, savedUsuari.getUsername());
        assertEquals(nom, savedUsuari.getNom());
        assertEquals(cognom, savedUsuari.getCognom());
        assertEquals(email, savedUsuari.getEmail());
        assertEquals(password, savedUsuari.getPassword());
        assertEquals("usuari", savedUsuari.getRol());
        assertEquals("registre.html", modelAndView.getViewName());
    }
}
