package org.springframework.samples.idus_martii.jugador;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {
	
	private final String  JUGADORES_LISTING_VIEW="/jugadores/jugadoresList";
	private final String  JUGADOR_PROFILE_VIEW="/jugadores/jugadorProfile";
	private static final String VIEWS_USUARIO_LISTING = "jugadores/userByPlayer";
	private final String  JUGADOR_ENVIAR_PETICIONES_AMISTAD_VIEW="/jugadores/enviarPeticionAmistad";
	private final JugadorService jugadorService;
	
	@Autowired
	public JugadorController(JugadorService jugadorService) {
		this.jugadorService = jugadorService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	   @Transactional(readOnly = true)
	   @GetMapping("/{jugadorId}/user")
	    public ModelAndView showJugador(@PathVariable("jugadorId") int jugadorId){
	        ModelAndView result=new ModelAndView(VIEWS_USUARIO_LISTING);
	        Jugador j = jugadorService.getJugadorById(jugadorId);
	        result.addObject("users", jugadorService.getUserByJugador(j));
	        return result;
	    }
	   /*
	    @GetMapping("/{jugadorId}/user")
	    public String show(@PathVariable("jugadorId") int jugadorId, ModelMap model){
	       Jugador j = jugadorService.getJugadorById(jugadorId); 
	        model.addAttribute("User", jugadorService.getUserByJugador(j));
	        return VIEWS_USUARIO_LISTING;
	    }*/
	
	@GetMapping(value = "/jugadores/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("jugador", new Jugador());
		return "jugador/findJugadores";
	}
	
	 @Transactional(readOnly = true)
	    @GetMapping("/")
	    public ModelAndView showJugadores(){
	        ModelAndView result=new ModelAndView(JUGADORES_LISTING_VIEW);
	        result.addObject("jugadores", jugadorService.getAll());
	        return result;
	    }
	 
	 
	 @Transactional(readOnly = true)
	    @GetMapping("/profile/{id}")
	    public ModelAndView showPerfilJugador(@PathVariable int id){
		 	Jugador jugador=jugadorService.getJugadorById(id);
		 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 	ModelAndView result=new ModelAndView(JUGADOR_PROFILE_VIEW);
	        result.addObject("jugador", jugador);
	        if(authentication!=null)
	        	if(authentication.isAuthenticated()) {
	        		User currentUser = (User) authentication.getPrincipal();
	        		System.out.println(currentUser.getUsername());
	        		
	        		Jugador jugadoractual = jugadorService.getByName(currentUser.getUsername());
	        		result.addObject("currentPlayer", jugadoractual);
	        	}
	        	else
	        		System.out.println("El usuario no está autentificado");
	        return result;
	    }
	 
	 	@Transactional()
	 	@GetMapping("/amigos/{idjugador}/{idamigo}")
	    public ModelAndView peticionamistad(@PathVariable int idjugador, @PathVariable int idamigo){
		 	Jugador jugador=jugadorService.getJugadorById(idjugador);
		 	Jugador amigo=jugadorService.getJugadorById(idamigo);
		 	
		 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 	ModelAndView result=new ModelAndView(JUGADOR_PROFILE_VIEW);
	        result.addObject("jugador", amigo);
	        result.addObject("currentPlayer", jugador);
	        if(authentication!=null)
	        	if(authentication.isAuthenticated()) {
	        		User currentUser = (User) authentication.getPrincipal();
	        		System.out.println(currentUser.getUsername());
	        		System.out.println(jugador.getUser().getUsername().toString());
	        		if(currentUser.getUsername().toString().equals(jugador.getUser().getUsername().toString())) {
	        			jugadorService.anadirAmigo(idjugador, idamigo);
	        			result.addObject("message", "Se ha enviado la petición de amistad");
	        		}else
	        			result.addObject("message", "No se ha podido enviar la petición de amistad");
	        	}
	        	else {
	        		System.out.println("El usuario no está autentificado");
	        	}
	        return result;
	    }
}
