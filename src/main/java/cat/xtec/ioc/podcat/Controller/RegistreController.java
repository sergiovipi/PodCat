package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
public class RegistreController {

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/registre")
    public ModelAndView registreUsuari(@RequestParam("username") String username,
                                       @RequestParam("nom") String nom,
                                       @RequestParam("cognom") String cognom,
                                       @RequestParam("email") String email,
                                       @RequestParam("password") String password) {

        Usuari usuari = new Usuari();
        usuari.setUsername(username);
        usuari.setNom(nom);
        usuari.setCognom(cognom);
        usuari.setEmail(email);
        usuari.setPassword(passwordEncoder.encode(password));
        usuari.setRol("usuari"); // rol per defecte
        usuariRepository.save(usuari);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registre.html");
        return modelAndView;
    }
}
