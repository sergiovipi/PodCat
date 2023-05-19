package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuariService {

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Usuari> getAllUsuaris() {
        return usuariRepository.findAll();
    }

    public Optional<Usuari> getUsuariById(Long id) {
        return usuariRepository.findById(id);
    }

    public Usuari getUserByUsernameAndPassword(String username, String password) {
        return usuariRepository.findByUsernameAndPassword(username, password);
    }

    public Usuari getUserByUsername(String username) {
        return usuariRepository.findByUsername(username);
    }

    public Usuari addUsuari(Usuari usuari) {
        return usuariRepository.save(usuari);
    }

    public Usuari updateFullUsuariById(Usuari request, Long id) {

        Usuari usuari = usuariRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuari no trobat"));

        usuari.setId(request.getId());
        usuari.setUsername(request.getUsername());
        usuari.setPassword(request.getPassword());
        usuari.setNom(request.getNom());
        usuari.setCognom(request.getCognom());
        usuari.setEmail(request.getEmail());
        usuari.setRol(request.getRol());

        return usuariRepository.save(usuari);
    }

    public Usuari updateUsuariFieldById(Usuari request, Long id) {

        Usuari usuari = usuariRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuari no trobat"));

        usuari.setUsername(request.getUsername());
        usuari.setNom(request.getNom());
        usuari.setCognom(request.getCognom());
        usuari.setEmail(request.getEmail());

        return usuariRepository.save(usuari);
    }

    public Boolean deleteUsuariById(Long id) {
        try {
            usuariRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Encrypt old passwords
//    public void encryptOldPasswords() {
//
//        List<Usuari> usuaris = usuariRepository.findAll();
//
//        for (Usuari usuari : usuaris) {
//            String encryptPassword = passwordEncoder.encode(usuari.getPassword());
//            usuari.setPassword(encryptPassword);
//        }
//        usuariRepository.saveAll(usuaris);
//    }
}
