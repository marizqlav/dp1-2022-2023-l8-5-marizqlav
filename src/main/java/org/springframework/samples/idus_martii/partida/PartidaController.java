package org.springframework.samples.idus_martii.partida;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.EstadoTurno;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
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
	private final String  VOTACIONES_DISPONIBLES_RONDA1_VIEW="/partidas/votacionesRonda1";
	private final String  VOTACIONES_DISPONIBLES_RONDA2_VIEW="/partidas/votacionesRonda2";
    PartidaService partidaService;
    JugadorService jugadorService;
    RondaService rondaService;
    TurnoService turnoService;
    FaccionService faccionService;

    @Autowired
    public PartidaController(PartidaService partidaService, JugadorService jugadorService, RondaService rondaService, TurnoService turnoService, FaccionService facionService) {
        this.partidaService = partidaService;
        this.jugadorService = jugadorService;
        this.rondaService = rondaService;
        this.turnoService = turnoService;
        this.faccionService = faccionService;
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

            partida.setFechaCreacion(LocalDateTime.now());
            partida.setJugador(jugador);
            this.partidaService.save(partida);

            int idPartida = partida.getId();
            

            int numj = partida.getNumeroJugadores();
            partida.setVotosTraidores(0);
            partida.setVotosLeales(0);
            partida.setLimite(2*numj +3 + numj/8);
            partidaService.anadirLobby(idPartida, idPartida);

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
            	//NO BORRAR, Servirá para los espectadores List<Jugador> jugadoresEnPartida = faccionService.getJugadoresPartida(partidaId);
            	
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
    public ModelAndView IniciarPartida(@PathVariable("partidaId") Integer partidaId) {
        Lobby lobby = partidaService.getLobby(partidaId);
        try {
            partidaService.IniciarPartida(partidaId, lobby);
            return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
        } catch (Exception e) {
            System.out.println(e);
            return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
        }
    }
    
    @GetMapping(value = "/juego/{partidaId}")
    public ModelAndView GetPartidaGeneral(@PathVariable("partidaId") Integer partidaId, HttpServletResponse response) throws Exception {
    	response.addHeader("Refresh", "1");

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	User currentUser = (User) authentication.getPrincipal();
    	Turno turno = partidaService.turnoActual(partidaId);
    	Partida partida = partidaService.findPartida(partidaId);
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
        Partida iniciada = partidaService.getPartidaIniciada(partida.getId());

    	if (iniciada == null) {
    		throw new Exception("Esta partida no ha sido iniciada");
    	}

    	ModelAndView result=new ModelAndView("/partidas/tablero");
        result.addObject("partida", partidaService.findPartida(partidaId));
        result.addObject("jugador", jugador);
        result.addObject("turno", turno);
        result.addObject("temporizador", LocalTime.of(LocalTime.now().minusHours(partida.getFechaInicio().toLocalTime().getHour()).getHour(), LocalTime.now().minusMinutes(partida.getFechaInicio().toLocalTime().getMinute()).getMinute(),  LocalTime.now().minusSeconds(partida.getFechaInicio().toLocalTime().getSecond()).getSecond()));
        return result;
    }
    
    @GetMapping(value = "/juego/{partidaId}/finalRonda")
    public ModelAndView getFinalRonda(@PathVariable("partidaId") Integer partidaId, Ronda rondaEnPartida, HttpServletResponse response) {
    	Integer numeroRonda = rondaEnPartida.getNumRonda();
        try {
        	partidaService.iniciarRondas(numeroRonda, rondaEnPartida.getPartida(), rondaEnPartida.getTurnos().get(rondaEnPartida.getTurnos().size()-1));
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
    }
    
    @GetMapping(value = "/juego/{partidaId}/cambiar")
    public ModelAndView GetPasarRolTurnosRondaPrimera(@PathVariable("partidaId") Integer partidaId, Turno turnoEnPartida, HttpServletResponse response) {
    	Integer numeroTurno = turnoEnPartida.getNumTurno();
        try {
        	partidaService.roles(numeroTurno, partidaId);
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
    }
    
    @PostMapping(value="partida/juego/{partidaId}/votar")
    public ModelAndView votacion(@PathVariable("partidaId") Integer partidaId, BindingResult result) {
    	 if (result.hasErrors()) {
             return new ModelAndView("partida/juego/{partidaId}/votar");
         }else {
        	 if(partidaService.rondaActual(partidaId).getNumRonda()==1
        			) {
        		 return new ModelAndView(VOTACIONES_DISPONIBLES_RONDA1_VIEW);
        	 }else {
        		 return new ModelAndView(VOTACIONES_DISPONIBLES_RONDA2_VIEW);
        	 }
        	 
        	 
         }
    	
    }

    @PostMapping(value="partida/juego/{partidaId}/votar/rojo")
    public ModelAndView votacionRojo(@PathVariable("partidaId") Integer partidaId, BindingResult result) {
    	 if (result.hasErrors()) {
             return new ModelAndView("partida/juego/{partidaId}/votar/rojo");
         }else {
        	 Turno turno = partidaService.turnoActual(partidaId);
        	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             User currentUser = (User) authentication.getPrincipal();
             Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
             try {
            	 turnoService.anadirVotoRojo(turno.getId(), jugador); 
             }catch(Exception e){
            	System.out.println(e);
             }
             return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
        	 
         }
    	
    }
    
    @PostMapping(value="partida/juego/{partidaId}/votar/verde")
    public ModelAndView votacionVerde(@PathVariable("partidaId") Integer partidaId, BindingResult result) {
    	 if (result.hasErrors()) {
             return new ModelAndView("partida/juego/{partidaId}/votar/verde");
         }else {
        	 Turno turno = partidaService.turnoActual(partidaId);
        	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             User currentUser = (User) authentication.getPrincipal();
             Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
             try {
            	 turnoService.anadirVotoVerde(turno.getId(), jugador); 
             }catch(Exception e){
            	System.out.println(e);
             }
             return new ModelAndView("redirect:/partida/juego/" + partidaId.toString()); 
         }
    	
    }

    @PostMapping(value="partida/juego/{partidaId}/votar/amarillo")
    public ModelAndView votacionAmarillo(@PathVariable("partidaId") Integer partidaId, BindingResult result) {
    	 if (result.hasErrors()) {
             return new ModelAndView("partida/juego/{partidaId}/votar/");
         }else {
        	 Turno turno = partidaService.turnoActual(partidaId);
        	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             User currentUser = (User) authentication.getPrincipal();
             Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
             try {
            	 turnoService.anadirVotoAmarillo(turno.getId(), jugador); 
             }catch(Exception e){
            	System.out.println(e);
             }
        	 return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
         }
    	
    
    }
}
