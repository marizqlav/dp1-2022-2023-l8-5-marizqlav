package org.springframework.samples.idus_martii.partida;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
	private final String PARTIDAS_LISTING_VIEW_FINALIZADAS ="/partidas/partidasList";
    private final String PARTIDAS_LISTING_VIEW_ACTUALES = "/partidas/partidasListActuales";
	private final String  PARTIDAS_DISPONIBLES_LISTING_VIEW="/partidas/partidasDisponiblesList";
	private final String  LOBBY_ESPERA_VIEW="/partidas/lobbyEspera";
    PartidaService partidaService;
    JugadorService jugadorService;

    @Autowired
    public PartidaController(PartidaService partidaService, JugadorService jugadorService) {
        this.partidaService = partidaService;
        this.jugadorService = jugadorService;
    }
    
    
    @Transactional(readOnly = true)
    @GetMapping("/disponibles")
    public ModelAndView showPartidas(HttpServletResponse response){
    	response.addHeader("Refresh", "5");
        ModelAndView result=new ModelAndView(PARTIDAS_DISPONIBLES_LISTING_VIEW);
        result.addObject("partidas", partidaService.getPartidasEnJuego());
        return result;
    }
    @Transactional(readOnly = true)
    @GetMapping("/finalizadas")
    public ModelAndView showPartidas(){
        ModelAndView result=new ModelAndView(PARTIDAS_LISTING_VIEW_FINALIZADAS);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
        result.addObject("partidas", partidaService.getPartidasFinalizadasJugador(jugador.getId()));
        return result;
    }

    @Transactional(readOnly = true)
    @GetMapping("/enJuego")
    public ModelAndView showPartidasEnJuego(){
        ModelAndView result=new ModelAndView(PARTIDAS_LISTING_VIEW_ACTUALES);
        result.addObject("partidas", partidaService.getPartidasEnJuego());
        return result;
    }


	@GetMapping(value = "/new")
	public String initCreationForm(Map<String, Object> model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
        Partida existe = partidaService.jugadorPartidaEnCurso(jugador.getId());
		if(existe==null) {
			Partida partida = new Partida();
			model.put("partida", partida);
			return VIEWS_PARTIDA_CREATE_OR_UPDATE_FORM;
		}else {
			 return "redirect:/partida/"+jugador.getId().toString()+"/"+existe.getId().toString();
		}
	}


	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Partida partida, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_PARTIDA_CREATE_OR_UPDATE_FORM;
		}
		else {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        User currentUser = (User) authentication.getPrincipal();
	        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
	        System.out.println(jugador.getUsername());
	        partida.setFechaCreacion(LocalDateTime.now());
	        partida.setJugador(jugador);
	        this.partidaService.save(partida);
	        partidaService.anadirLobby(partida.getId(),partida.getId());
	        return "redirect:/partida/"+jugador.getId().toString()+"/"+partida.getId().toString();
		}
	        	
	}
	
	
    @GetMapping(value = "/{jugadorId}/{partidaId}")
    public ModelAndView lobby(@PathVariable("partidaId") Integer partidaId,@PathVariable("jugadorId") Integer jugadorId,HttpServletResponse response) {
    	response.addHeader("Refresh", "10");
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	Partida partida = partidaService.findPartida(partidaId);
    	User currentUser = (User) authentication.getPrincipal();
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
        
        if(partidaService.estaJugadorLobby(jugador.getId(), partidaId)==null)
        	partidaService.anadirJugadorLobby(jugador.getId(),partidaId);
        System.out.println(partidaService.estaJugadorLobby(jugador.getId(), partidaId));
        List<Jugador> enlobby = partidaService.getLobby(partidaId).getJugadores();
        Partida iniciada = partidaService.getPartidaIniciada(partida.getId());
            if(iniciada!=null) {
            	ModelAndView result=new ModelAndView("redirect:/partida/juego/"+partida.getId().toString()+"/"+jugador.getId().toString());
            	return result;
            	}
            else {
            	ModelAndView result=new ModelAndView(LOBBY_ESPERA_VIEW);
            	result.addObject("jugadores", enlobby);
            	result.addObject("partida", partida);
            	return result;
            }
	}
    

    
   
    @Transactional(readOnly = true)
    @GetMapping(value = "/{partidaId}/iniciar")
    public ModelAndView IniciarPartida(@PathVariable("partidaId") Integer partidaId) {
        try {
            partidaService.IniciarPartida(partidaId);
        } catch (InitiationException e) {
            //Ignorar
        }
        return new ModelAndView("redirect:/partida/{partidaId}");
    }

    @GetMapping(value = "/juego/{partidaId}/{jugadorId}")
    public ModelAndView GetPartidaGeneral(@PathVariable("partidaId") Integer partidaId,@PathVariable("jugadorId") Integer jugadorId,HttpServletResponse response) {
    	response.addHeader("Refresh", "10");
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	Partida partida = partidaService.findPartida(partidaId);
    	User currentUser = (User) authentication.getPrincipal();
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
        Partida iniciada = partidaService.getPartidaIniciada(partida.getId());
    	if(iniciada==null)
    		partida.setFechaInicio(LocalDateTime.now());
    		partidaService.save(partida);
    	ModelAndView result=new ModelAndView("/partidas/tablero");
        result.addObject("partida", partidaService.findPartida(partidaId));
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        return result;
    }
    
    
}
