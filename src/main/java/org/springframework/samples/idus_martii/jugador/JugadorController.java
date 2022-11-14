package org.springframework.samples.idus_martii.jugador;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

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
public class JugadorController {
	private static final String VIEWS_JUGADOR_CREATE_FORM = "jugadores/createOrUpdateJugadorForm";
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
	
	 
	 @Transactional(readOnly = true)
	    @GetMapping("/jugadores/profile/{id}")
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
	 	@GetMapping("/jugadores/amigos/{idjugador}/{idamigo}")
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
	 	
	 	@GetMapping(value = "/jugadores/find")
		public String initFindForm(Map<String, Object> model) {
			model.put("jugador", new Jugador());
			return "jugadores/findJugadores";
		}

		@GetMapping(value = "/jugadores")
		public String processFindForm(Jugador jugador, BindingResult result, Map<String, Object> model) {

			// allow parameterless GET request for /jugadores to return all records
			if (jugador.getUsername() == null) {
				jugador.setUsername(""); // empty string signifies broadest possible search
			}

			// find jugadors by last name
			Collection<Jugador> results = this.jugadorService.getJugadorByUsername(jugador.getUsername());
			//Collection<Jugador> results = this.jugadorService.getAll();
			if (results.isEmpty()) {
				// no jugadores found
				result.rejectValue("username", "notFound", "not found");
				return "/jugadores/findJugadores";
			}
			else if (results.size() == 1) {
				// 1 jugador found
				jugador = results.iterator().next();
				return "redirect:/jugadores/profile/" + jugador.getId();
			}
			else {
				// multiple jugadores found
				model.put("selections", results);
				return "jugadores/jugadoresList";
			}
		}
		
		@GetMapping(value = "/new")
		public String initCreationForm(Map<String, Object> model) {
			Jugador jugador = new Jugador();
			model.put("jugador", jugador);
			//TODO
			return VIEWS_JUGADOR_CREATE_FORM;
		}
	
		@PostMapping(value = "/new")
		public String processCreationForm(@Valid Jugador jugador, BindingResult result) {
			//TODO
			if (result.hasErrors()) {
				return VIEWS_JUGADOR_CREATE_FORM;
			}
			else {
				//creating owner, user, and authority
				this.jugadorService.save(jugador);
				return "redirect:/";
			}
		}
}
