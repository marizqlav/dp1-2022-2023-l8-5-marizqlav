package org.springframework.samples.idus_martii.jugador;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JugadorController {
	
	private static final String VIEWS_JUGADOR_CREATE_FORM = "jugadores/createOrUpdateJugadorForm";
	private final String JUGADORES_LISTING_VIEW="/jugadores/jugadoresList";
	private final String JUGADORES_AMIGOS_VIEW="/jugadores/jugadoresAmigosList";
	private final String JUGADOR_PROFILE_VIEW="/jugadores/jugadorProfile";
	private static final String VIEWS_USUARIO_LISTING = "jugadores/userByPlayer";
	private final String PETICIONES_AMISTAD_VIEW="/jugadores/peticionesAmistadList";
	
	
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
	   
	@Transactional(readOnly = true)
	@GetMapping("/jugadores/profile/{id}")
	public ModelAndView showPerfilJugador(@PathVariable int id){
		Jugador jugador=jugadorService.getJugadorById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView result=new ModelAndView(JUGADOR_PROFILE_VIEW);
	    result.addObject("jugador", jugador);
	    	if(authentication!=null) {
	    		if(authentication.isAuthenticated()) {
	        		User currentUser = (User) authentication.getPrincipal();
	        		Jugador jugadoractual = jugadorService.getByName(currentUser.getUsername());
	        		if(jugadoractual!=null) {
	        			result.addObject("currentPlayer", jugadoractual);
		        		Boolean noSonAmigos = jugadorService.noSonAmigos(jugadoractual.getId(), jugador.getId());
		        		result.addObject("noSonAmigos", noSonAmigos);
		        		if(jugadoractual.getId() == jugador.getId()) {
			        		Boolean esTuPerfil=true;
			        		result.addObject("esTuPerfil", esTuPerfil);
			        	}
	        		}

	        	}
	        }
	        return result;
	    }
	 
	@Transactional(readOnly = true)
	@GetMapping("/jugadores/profile/nombre/{nombre}")
	public String irPerfilJugador(@PathVariable("nombre") String nombre){
	 	Jugador jugador=jugadorService.getByName(nombre);
	 	return "redirect:/jugadores/profile/"+jugador.getId().toString();
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
	       		if(currentUser.getUsername().toString().equals(jugador.getUser().getUsername().toString()) && idjugador!=idamigo) {
	       			jugadorService.anadirAmigo(idjugador, idamigo);
	       			result.addObject("message", "Se ha enviado la petición de amistad");
	       		}else
	       			result.addObject("message", "No se ha podido enviar la petición de amistad");
	       	}
	    return result;
	}	       
	       
	@GetMapping(value = "/jugadores/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("jugador", new Jugador());
		return "/jugadores/findJugadores";
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
			return JUGADORES_LISTING_VIEW;
		}
	}
		
	@GetMapping(value = "/jugadores/{pagina}")
	public ModelAndView processFindFormPaginated(Jugador jugador, BindingResult result, Map<String, Object> model, @PathVariable("pagina") Integer pagina) {
		String url = "";
		// allow parameterless GET request for /jugadores to return all records
		if (jugador.getUsername() == null) {
			jugador.setUsername(""); // empty string signifies broadest possible search
		}
		// find jugadors by last name
		List<Jugador> results = jugadorService.getPlayersPaginated(this.jugadorService.getJugadorByUsername(jugador.getUsername())).get(pagina-1);
		int max = jugadorService.getPlayersPaginated(this.jugadorService.getJugadorByUsername(jugador.getUsername())).size();
		//Collection<Jugador> results = this.jugadorService.getAll();
		
		if (results.isEmpty()) {
			// no jugadores found
			result.rejectValue("username", "notFound", "not found");
			url = "/jugadores/findJugadores";
		}
		else if (results.size() == 1) {
			// 1 jugador found
			jugador = results.iterator().next();
			url = "redirect:/jugadores/profile/" + jugador.getId();
		}
		else {
			// multiple jugadores found
			model.put("selections", results);
			model.put("busqueda", "?user.username="+ result.getFieldValue("username"));
			model.put("ultima", max);
			url = JUGADORES_LISTING_VIEW;
		}
		ModelAndView modeloView =new ModelAndView(url);
		modeloView.addObject("pagina", pagina);
		return modeloView;
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
		
	@GetMapping(value = "/jugadores/peticiones")
	public ModelAndView peticiones(HttpServletResponse response) {
	   	response.addHeader("Refresh", "10");
	   	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	   	User currentUser = (User) authentication.getPrincipal();
	   	Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
	    List<Jugador> peticiones = jugadorService.getpeticionesAmistadJugador(jugador.getId());
	    ModelAndView result=new ModelAndView(PETICIONES_AMISTAD_VIEW);
	    result.addObject("peticiones", peticiones);
	    return result;          
	}
		
	@GetMapping(value = "/jugadores/peticiones/rechazar/{rechazadoId}")
	public ModelAndView rechazar(@PathVariable("rechazadoId") Integer rechazadoId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
	    Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
	    ModelAndView result=new ModelAndView(PETICIONES_AMISTAD_VIEW);
	    if(jugadorService.sonAmigos(jugador.getId(), rechazadoId)!=null) {
	       	jugadorService.rechazarPeticion(jugador.getId(), rechazadoId);
	       	result.addObject("message", "Solicitud de amistad rechazada");
	    }
	    return result;          
	}
		
	@GetMapping(value = "/jugadores/peticiones/aceptar/{aceptadoId}")
	public ModelAndView aceptar(@PathVariable("aceptadoId") Integer aceptadoId) {
	  	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	   	User currentUser = (User) authentication.getPrincipal();
	   	Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
	    ModelAndView result=new ModelAndView(PETICIONES_AMISTAD_VIEW);
	    if(jugadorService.sonAmigos(jugador.getId(), aceptadoId)!=null) {
	      	jugadorService.anadirAmigo(jugador.getId(),aceptadoId);
	       	result.addObject("message", "Solicitud de amistad aceptada");
	    }
	    return result;          
	}
		
	@GetMapping(value = "/jugadores/amigos")
	public ModelAndView amigos(HttpServletResponse response) {
	   	response.addHeader("Refresh", "10");
	   	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	   	User currentUser = (User) authentication.getPrincipal();
	   	Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);    
	    List<Jugador> amigos = jugadorService.getAmigos(jugador.getId());
	    ModelAndView result=new ModelAndView(JUGADORES_AMIGOS_VIEW);
	    result.addObject("selections", amigos);
	    return result;          
	}
		
	@Transactional()
	@GetMapping("/jugadores/amigos/eliminar/{jugadorId}")
	public ModelAndView deleteAmigo(@PathVariable int jugadorId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	   	User currentUser = (User) authentication.getPrincipal();
		Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
		jugadorService.deleteAmigo(jugador.getId(),jugadorId);        
		ModelAndView result= new ModelAndView(JUGADORES_AMIGOS_VIEW);
		result.addObject("message", "Amigo eliminado correctamente");
		return result;
	}

	@Transactional(readOnly = true)
	@GetMapping("/jugadores/profile/{id}/edit")
	public ModelAndView editJugador(@PathVariable int id){
		Jugador jugador =jugadorService.getJugadorById(id);        
		ModelAndView result=new ModelAndView(VIEWS_JUGADOR_CREATE_FORM);
	    result.addObject("jugador", jugador);
	    return result;
	}

	@Transactional
	@PostMapping("/jugadores/profile/{id}/edit")
	public ModelAndView saveJugador(@PathVariable int id,@Valid Jugador jugador){
	    Jugador jugadorEditar=jugadorService.getJugadorById(id);
	    BeanUtils.copyProperties(jugador,jugadorEditar,"id");
	    jugadorService.save(jugadorEditar);
	    ModelAndView result=showPerfilJugador(id);
	    result.addObject("message", "El jugador se ha actualizado correctamente");
	    return result; 
	}

	@Transactional()
	@GetMapping("/jugadores/eliminar/{jugadorId}")
	public ModelAndView deleteJugador(@PathVariable int jugadorId){
		jugadorService.deleteJugadorById(jugadorId);        
		ModelAndView result= new ModelAndView("welcome");
		result.addObject("message", "El jugador se ha eliminado correctamente");
		return result;
	}
}
