package org.springframework.samples.idus_martii.partida;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

        ModelAndView result = new ModelAndView(PARTIDAS_DISPONIBLES_LISTING_VIEW);
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
    public ModelAndView showPartidasEnJuego(HttpServletResponse response){
    	response.addHeader("Refresh", "10");
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
		
        if(existe == null) {
			Partida partida = new Partida();
			model.put("partida", partida);
			return VIEWS_PARTIDA_CREATE_OR_UPDATE_FORM;

		}else {
			 return "redirect:/partida/" + existe.getId().toString();
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
	        //System.out.println(jugador.getUsername());
	        
            partida.setFechaCreacion(LocalDateTime.now());
	        partida.setJugador(jugador);
	        this.partidaService.save(partida);

	        partidaService.anadirLobby(partida.getId(),partida.getId());

	        return "redirect:/partida/" + partida.getId().toString();
		}
	        	
	}
	
	@GetMapping(value = "/juego/{partidaId}/cancelar")
    public ModelAndView CancelarPartida(@PathVariable("partidaId") Integer partidaId) {
    	Partida partida = partidaService.findPartida(partidaId);
        Partida iniciada = partidaService.jugadorPartidaEnCurso(partida.getJugador().getId());
        
    	if(iniciada == null) {
    		ModelAndView result=new ModelAndView("redirect:/");

            result.addObject("message", "No se ha encontrado una partida que cancelar");
            return result;
    	
        }else{
            partidaService.cancelarPartida(partidaId);

            ModelAndView result=new ModelAndView("redirect:/");
            result.addObject("message", "Se ha cancelado la partida correctamente");
            return result;
        }
    }
	
    @GetMapping(value = "/{partidaId}")
    public ModelAndView lobby(@PathVariable("partidaId") Integer partidaId, HttpServletResponse response) {
    	response.addHeader("Refresh", "10");
        
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	//TODO comprobar que el jugador no este ya en la partida
    	User currentUser = (User) authentication.getPrincipal();
        Partida partida = partidaService.findPartida(partidaId);
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
        
        if(partidaService.findJugadorInLobby(jugador.getId(), partidaId)==null)
        	partidaService.anadirJugadorLobby(jugador.getId(),partidaId);

        List<Jugador> enlobby = partidaService.getLobby(partidaId).getJugadores();

        if(partida != null) {
        	Partida iniciada = partidaService.getPartidaIniciada(partida.getId());

            if (iniciada != null && partida.getNumeroJugadores() == enlobby.size()) {

            	ModelAndView result=new ModelAndView("redirect:/partida/juego/" + partida.getId().toString());
            	return result;

            	} else {
             		ModelAndView result=new ModelAndView(LOBBY_ESPERA_VIEW);
                	result.addObject("jugadores", enlobby);
                	result.addObject("partida", partida);
                	return result;
                 }

            }else {
            	ModelAndView result=new ModelAndView("redirect:/");
                result.addObject("message", "Se ha cancelado la partida");
                return result;
            }
	}
   
    @GetMapping(value = "/juego/{partidaId}/iniciar")
    public ModelAndView IniciarPartida(@PathVariable("partidaId") Integer partidaId) throws InitiationException {

        Lobby lobby = partidaService.getLobby(partidaId);

        partidaService.IniciarPartida(partidaId, lobby);

        return new ModelAndView("redirect:/partida/juego/{partidaId}");
    }

   
    
    @GetMapping(value = "/juego/{partidaId}")
    public ModelAndView GetPartidaGeneral(@PathVariable("partidaId") Integer partidaId, HttpServletResponse response) {
    	response.addHeader("Refresh", "10");

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	User currentUser = (User) authentication.getPrincipal();

    	Partida partida = partidaService.findPartida(partidaId);
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
        Partida iniciada = partidaService.getPartidaIniciada(partida.getId());
    	if(iniciada==null) {
    		partida.setFechaInicio(LocalDateTime.now());
    		partidaService.save(partida);
    		}
    	ModelAndView result=new ModelAndView("/partidas/tablero");
        result.addObject("partida", partidaService.findPartida(partidaId));
        return result;
    }
    
}
