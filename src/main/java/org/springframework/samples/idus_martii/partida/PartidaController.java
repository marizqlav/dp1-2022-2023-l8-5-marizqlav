package org.springframework.samples.idus_martii.partida;


import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/partida")
public class PartidaController {

	private static final String VIEWS_PARTIDA_CREATE_OR_UPDATE_FORM = "partidas/createOrUpdatePartidaForm";
	private final String  PARTIDAS_LISTING_VIEW="/partidas/partidasList";
    PartidaService partidaService;

    @Autowired
    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/")
    public ModelAndView showPartidas(HttpServletResponse response){
    	response.addHeader("Refresh", "5");
        ModelAndView result=new ModelAndView(PARTIDAS_LISTING_VIEW);
        result.addObject("partidas", partidaService.getPartidas());
        return result;
    }

	@GetMapping(value = "/new")
	public String initCreationForm(Map<String, Object> model) {
		Partida partida = new Partida();
		model.put("partida", partida);
		return VIEWS_PARTIDA_CREATE_OR_UPDATE_FORM;
	}


	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Partida partida, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_PARTIDA_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.partidaService.save(partida);
			
			return "redirect:/partida/{userId}/" + partida.getId();
		}
	}

    /*
    @Transactional(readOnly = true)
    @GetMapping("/finalizadas")
    public ModelAndView showPartidas(HttpServletResponse response){
        //response.addHeader("Refresh", "5");
        ModelAndView result=new ModelAndView(PARTIDAS_LISTING_VIEW_ACTUALES);
        result.addObject("partidas", partidaService.getPartidas());
        return result;
    }

    @Transactional(readOnly = true)
    @GetMapping("/enJuego")
    public ModelAndView showPartidasEnJuego(HttpServletResponse response){
        //response.addHeader("Refresh", "5");
        ModelAndView result=new ModelAndView(PARTIDAS_LISTING_VIEW_ACTUALES);
        result.addObject("partidas", partidaService.getPartidasEnJuego());
        return result;
    }
*/
    @GetMapping(value = "/create")
    public ModelAndView CrearPartidaForm() {
        //TODO Mario y Pablo
        return null;
    }

    @PostMapping(value = "/create")
    public ModelAndView CrearPartida() {
        //TODO Mario y Pablo
        return null;
    }
    
    @GetMapping(value = "/{partidaId}/iniciar")
    public ModelAndView IniciarPartida(@PathVariable("partidaId") Integer partidaId) {
        partidaService.IniciarPartida(partidaId);
        return new ModelAndView("redirect:/partida/{userId}/{partidaId}");
    }

    @GetMapping(value = "/create/{partidaId}")
    public ModelAndView GetPartidaGeneral(@PathVariable("partidaId") Integer partidaId) {
    	ModelAndView result=new ModelAndView("/partidas/tablero");
        result.addObject("partida", partidaService.findPartida(partidaId));
        return result;
    }
    
    
}
