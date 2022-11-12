package org.springframework.samples.idus_martii.jugador;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.owner.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/jugadores")
public class JugadorContoller {
	
	private static final String VIEWS_JUGADORES_LISTING = "jugadores/jugadoresListing";
	private static final String VIEWS_USUARIO_LISTING = "jugadores/userByPlayer";
	
	private final JugadorService jugadorService;
	@Autowired
	public JugadorContoller(JugadorService jugadorService) {
		this.jugadorService = jugadorService;
	}
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	   @Transactional(readOnly = true)
	    @GetMapping("/")
	    public ModelAndView showJugadores(){
	        ModelAndView result=new ModelAndView(VIEWS_JUGADORES_LISTING);
	        result.addObject("jugadores", jugadorService.getAll());
	        return result;
	    }
	   
	   @Transactional(readOnly = true)
	    @GetMapping("/{username}")
	    public ModelAndView show(){
	        ModelAndView result=new ModelAndView(VIEWS_USUARIO_LISTING);
	        result.addObject("jugadores", jugadorService.getUserByJugador(jugadorService.getJugadorById(1)));
	        return result;
	    }
	
	@GetMapping(value = "/jugadores/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("jugador", new Jugador());
		return "jugador/findJugadores";
	}
	

	

}
