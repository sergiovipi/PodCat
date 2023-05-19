package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Service.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080, http://localhost:8080")
@RequestMapping(path = "api/v1/usuaris")
public class UsuariController {

    @Autowired
    private UsuariService usuariService;

    @GetMapping()
    public List<Usuari> getAllUsuaris() {
        return this.usuariService.getAllUsuaris();
    }

    @GetMapping(path = "/{id}")
    public Optional<Usuari> getUsuariById(@PathVariable("id") Long id) {
        return this.usuariService.getUsuariById(id);
    }

    @PostMapping
    public Usuari addUsuari(@RequestBody Usuari usuari) {
        return this.usuariService.addUsuari(usuari);
    }

    @PutMapping(path = "/{id}")
    public Usuari updateFullUsuariById(@RequestBody Usuari request, @PathVariable("id") Long id) {
        return this.usuariService.updateFullUsuariById(request, id);
    }

    @PatchMapping(path = "/{id}")
    public Usuari updateUsuariFieldById(@RequestBody Usuari request, @PathVariable("id") Long id) {
        return this.usuariService.updateUsuariFieldById(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUsuariById(@PathVariable("id") Long id) {

        boolean okDelete = usuariService.deleteUsuariById(id);

        if (okDelete) {
            return ResponseEntity.ok("Usuari amb id: " + id + " ha sigut eliminat!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No es pot eliminar l'usuari. Error.");
        }
    }

    // Encrypt old passwords
//    @PostConstruct
//    public void encriptarContrasenasExistentes() {
//        usuariService.encryptOldPasswords();
//    }
}

