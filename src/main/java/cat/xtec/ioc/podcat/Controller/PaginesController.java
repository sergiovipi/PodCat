package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;

import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Repository.PodcastRepository;
import cat.xtec.ioc.podcat.Service.CanalService;
import cat.xtec.ioc.podcat.Service.PodcastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class PaginesController {

    @Autowired
    private CanalService canalService;

    @Autowired
    private PodcastService podcastService;

    @Autowired
    private PodcastRepository podcastRepository;

    @RequestMapping("")
    public String index(Model model) {
        List<Podcast>listPodcast = podcastService.getPodcastsByIdDesc();
        model.addAttribute("listPodcast", listPodcast);
        return "index";
    }
    /* ANTERIOR
    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    } */

    @RequestMapping("admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin.html");
        return modelAndView;
    }

    @RequestMapping("contacte")
    public ModelAndView contacte() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contacte.html");
        return modelAndView;
    }

    @RequestMapping("podcast")
    public String viewCanalPodcast(Model model) {
        List<Canal>listaCanal = canalService.getCanals();
        List<Podcast>listaPodcast = podcastService.getPodcasts();
        model.addAttribute("listaCanal", listaCanal);
        model.addAttribute("listaPodcast", listaPodcast);
        return "podcast";
    }

    @RequestMapping("filtre")
    public String filtre(Model model, @Param("keyword") String keyword) {
        List<Podcast> listPodcast = podcastService.listAll(keyword);
        model.addAttribute("listPodcast", listPodcast);
        model.addAttribute("keyword", keyword);
        return "filtre";
    }

    @GetMapping("/podcast/{id}")
    public String buscaPodcastById(@PathVariable("id")Long id, Model model){
        Podcast p = podcastRepository.getOne(id);
        // List<Podcast>listPodcast = podcastService.getPodcasts();
        List<Podcast> listPodcast = podcastService.getPodcastsByCanal(p.getCanal());
        model.addAttribute("listPodcast", listPodcast);
        model.addAttribute("podcast",p);
        return "descpodcast";
    }
}
