package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.CanalRepository;
import cat.xtec.ioc.podcat.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PerfilController {

    @Autowired
    private CanalService canalService;

    @Autowired
    private PodcastService podcastService;

    @Autowired
    private UsuariService usuariService;

    @Autowired
    private CanalRepository canalRepository;

    @Autowired
    private AudioService audioService;

    @Autowired
    private ImageService imageService;
    
    // Pàgina de perfil
    @RequestMapping("perfil")
    public String perfil(Model model, @Param("keyword") String keyword, HttpSession session) {
        Usuari usuari = (Usuari) session.getAttribute("usuari");
        List<Canal> listsCanals = canalService.getCanalsByUsuari(usuari);
        List<Podcast> listsPodcasts = podcastService.getPodcastsByUsuari(usuari);
        model.addAttribute("llistaCanals", listsCanals);
        model.addAttribute("llistaPodcasts", listsPodcasts);
        model.addAttribute("keyword", keyword);
        return "perfil";
    }
    
    // Nou Canal
    @PostMapping("canal/afegir")
    public ModelAndView nouCanal(   @RequestParam("titol") String titol,
                                    @RequestParam("descripcio") String descripcio,
                                    Model model, HttpSession session) {
        Usuari usuari = (Usuari) session.getAttribute("usuari");
        Canal canal = new Canal();
        canal.setUsuari(usuari);
        canal.setTitol(titol);
        canal.setDescripcio(descripcio);
        canalRepository.save(canal);

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("iTitol", titol);
        model.addAttribute("iDesc", descripcio);
        modelAndView.setViewName("perfil");
        return modelAndView;
    }

    // Formulari Modificar CANAL
    @PostMapping("canal/formModCanal")
    public ModelAndView formModCanal(@RequestParam("formModCanal") Long idCanal,
                                    Model model, HttpSession session) {
        Optional<Canal> canalSelecionat = canalService.getCanalById(idCanal);
        Canal canal = canalSelecionat.get();

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("formModCanal", idCanal);
        model.addAttribute("formTitolCanal", canal.getTitol());
        model.addAttribute("formDescCanal", canal.getDescripcio());
        modelAndView.setViewName("perfil");
        return modelAndView;
    }

    // Modificar CANAL
    @PostMapping("canal/modCanal")
    public ModelAndView modCanal(  @RequestParam("modCanalID") Long idCanal,
                                    @RequestParam("modCanalTitol") String titol,
                                    @RequestParam("modCanalDesc") String descripcio,
                                    Model model) {
        // Obtenim el Canal
        Optional<Canal> canalSelecionat = canalService.getCanalById(idCanal);
        Canal canal = canalSelecionat.get();

        canal.setTitol(titol);
        canal.setDescripcio(descripcio);

        // Modifiquem el Canal
        canalService.updateFullCanalById(canal, idCanal);

        // Pàgina amb avis d'actualització
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("canalModificat", titol);
        modelAndView.setViewName("perfil");
        return modelAndView;
    }

    // Eliminar Canal
    @PostMapping("canal/eliminar")
    public ModelAndView eliminaCanal(   @RequestParam("eliminaIDcanal") Long eliminaIDcanal,
                                        @RequestParam("eliminaNomCanal") String eliminaNomCanal,
                                        Model model) {
        // Eliminem el Canal
        canalService.deleteCanalById(eliminaIDcanal);
        // Creem el model posterior
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("eCanal", eliminaNomCanal);
        modelAndView.setViewName("perfil");
        return modelAndView;
    }


    // Formulari Nou Podcast
    @PostMapping("podcast/afegir")
    public ModelAndView formPodcast(@RequestParam("idCanal") Long idCanal,
                                    Model model, HttpSession session) {
        Optional<Canal> canalSelecionat = canalService.getCanalById(idCanal);
        Canal canal = canalSelecionat.get();

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("idCanal", idCanal);
        model.addAttribute("titolCanal", canal.getTitol());
        modelAndView.setViewName("perfil");
        return modelAndView;
    }

    // Nou Podcast
    @PostMapping("podcast/nouPodcast")
    public ModelAndView nouPodcast (    @RequestParam("titol") String titol,
                                        @RequestParam("descripcio") String descripcio,
                                        @RequestParam("genere") String genere,
                                        @RequestParam("etiquetes") String etiquetes,
                                        @RequestParam("imatge") MultipartFile imatge,
                                        @RequestParam("audio") MultipartFile audio,
                                        @RequestParam("idCanal") Long idCanal,
                                        Model model, HttpSession session) throws IOException {

        Usuari usuari = (Usuari) session.getAttribute("usuari");
        Optional<Canal> canalSelecionat = canalService.getCanalById(idCanal);
        Canal canal = canalSelecionat.get();
        Podcast podcast = new Podcast();
        podcast.setUsuari(usuari);
        podcast.setCanal(canal);
        podcast.setTitol(titol);
        podcast.setDescripcio(descripcio);
        podcast.setGenere(genere);
        podcast.setEtiquetes(etiquetes);
        audioService.uploadAudio(podcast, audio);
        imageService.uploadImage(podcast, imatge);

        podcastService.addPodcast(podcast);

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("titol", titol);
        model.addAttribute("descripcio", descripcio);
        model.addAttribute("genere", genere);
        model.addAttribute("etiquetes", etiquetes);
        model.addAttribute("imatge", imatge);
        model.addAttribute("audio", audio);
        modelAndView.setViewName("perfil");
        return modelAndView;
    }

    // Formulari Modificar PODCAST
    @PostMapping("podcast/formModPodcast")
    public ModelAndView formModPodcast( @RequestParam("formModPodcastID") Long idPodcast,
                                        Model model) {
        
        Optional<Podcast> podcastSelecionat = podcastService.getPodcastById(idPodcast);
        Podcast podcast = podcastSelecionat.get();

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("formModPodcastID", idPodcast);
        model.addAttribute("formTitolPodcast", podcast.getTitol());
        model.addAttribute("formDescPodcast", podcast.getDescripcio());
        model.addAttribute("formGenerePodcast", podcast.getGenere());
        model.addAttribute("formEtiquetesPodcast", podcast.getEtiquetes());
        modelAndView.setViewName("perfil");
        return modelAndView;
    }

    // Modificar PODCAST
    @PostMapping("podcast/modPodcast")
    public ModelAndView modCanal(  @RequestParam("modPodcastID") Long idPodcast,
                                    @RequestParam("modPodcastTitol") String titol,
                                    @RequestParam("modPodcastDesc") String descripcio,
                                    @RequestParam("modPodcastGenere") String genere,
                                    @RequestParam("modPodcastEtiquetes") String etiquetes,
                                    Model model) {
        // Obtenim el Canal
        Optional<Podcast> podcastSelecionat = podcastService.getPodcastById(idPodcast);
        Podcast podcast = podcastSelecionat.get();

        podcast.setTitol(titol);
        podcast.setDescripcio(descripcio);
        podcast.setGenere(genere);
        podcast.setEtiquetes(etiquetes);

        // Modifiquem el Canal
        podcastService.updateFullPodcastById(podcast, idPodcast);

        // Pàgina amb avis d'actualització
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("podcastModificat", titol);
        model.addAttribute("podcastIDmod", idPodcast);
        modelAndView.setViewName("perfil");
        return modelAndView;
    }

    // Eliminar Podcast
    @PostMapping("podcast/eliminaPodcast")
    public ModelAndView eliminaPodcast(   @RequestParam("eliminaIDpodcast") Long eliminaIDpodcast,
                                        @RequestParam("eliminaNomPodcast") String eliminaNomPodcast,
                                        Model model) {
        // Eliminem el Podcast
        podcastService.deletePodcastById(eliminaIDpodcast);
        // Creem el model posterior
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("ePodcast", eliminaNomPodcast);
        modelAndView.setViewName("perfil");
        return modelAndView;
    }

    // Modificar usuari
    @PostMapping("perfil/modificar")
    public ModelAndView modUsuari(  @RequestParam("username") String username,
                                    @RequestParam("nom") String nom,
                                    @RequestParam("cognom") String cognom,
                                    @RequestParam("email") String email,
                                    HttpSession session, Model model) {
        // Obtenim l'usuari de la sessió
        Usuari usuari = (Usuari) session.getAttribute("usuari");

        usuari.setUsername(username);
        usuari.setNom(nom);
        usuari.setCognom(cognom);
        usuari.setEmail(email);

        // Modifiquem l'usuari
        usuariService.updateUsuariFieldById(usuari, usuari.getId());

        // Set sessió usuari actualitzat
        session.setAttribute("usuari", usuari);

        // Pàgina amb avis d'actualització
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("modUsername", username);
        modelAndView.setViewName("perfil");
        return modelAndView;
        // Redirect /perfil
        // return new ModelAndView("redirect:/perfil");
    }
}
