package org.springframework.samples.idus_martii.partida;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.mensaje.Mensaje;
import org.springframework.samples.idus_martii.mensaje.MensajeService;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.EstadoTurno;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.samples.idus_martii.turno.VotosTurno;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    RondaService rondaService;
    TurnoService turnoService;
    FaccionService faccionService;
    MensajeService mensajeService;

    @Autowired
    public PartidaController(PartidaService partidaService, JugadorService jugadorService, RondaService rondaService, TurnoService turnoService, FaccionService faccionService, MensajeService mensajeService) {
        this.partidaService = partidaService;
        this.jugadorService = jugadorService;
        this.rondaService = rondaService;
        this.turnoService = turnoService;
        this.faccionService = faccionService;
        this.mensajeService = mensajeService;
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
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
    }
    
    @GetMapping(value = "/juego/{partidaId}")
    public ModelAndView GetPartidaGeneral(@PathVariable("partidaId") Integer partidaId, HttpServletResponse response, @RequestParam(value="mensaje",required = false) String cuerpomensaje) throws Exception {
    	response.addHeader("Refresh", "5");
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	User currentUser = (User) authentication.getPrincipal();
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);

        List<Mensaje> mensajes = mensajeService.getMensajesByPartidaId(partidaId);

    	Turno turno = partidaService.getTurnoActual(partidaId);
    	Ronda ronda = partidaService.getRondaActual(partidaId);
    	Partida partida = partidaService.findPartida(partidaId);
    	VotosTurno vototurno = turnoService.conocerVoto(turno.getId(), jugador.getId());
        Partida iniciada = partidaService.getPartidaIniciada(partida.getId());
        Integer votosFavor = partidaService.getVotosFavor(partidaId);
        Integer votosContra = partidaService.getVotosContra(partidaId);
        Faccion faccion = faccionService.getFaccionJugadorPartida(jugador.getId(),partidaId);
       
    	if(cuerpomensaje!=null) {
    		Mensaje nmensaje = new Mensaje();
            //nmensaje.setHora(LocalTime.now());
            nmensaje.setJugador(jugador);
            nmensaje.setPartida(partida);
            nmensaje.setTexto(cuerpomensaje);
            mensajeService.save(nmensaje);
    	}
        
        
        if (iniciada == null) {
    		throw new Exception("Esta partida no ha sido iniciada");
    	}
        String aviso="";
        //Redirection
        ModelAndView result = new ModelAndView("/partidas/tablero");
        if(turno.getNumTurno()==1 && turno.getEstadoTurno()==EstadoTurno.Elegir_rol){
        	turnoService.continuarTurno(turno.getId());
        }
        if(turno.getVotosLeales()==2 || turno.getVotosLeales()==2 || (turno.getVotosLeales()==1 && turno.getVotosTraidores()==1)) {
        	turno.setEstadoTurno(EstadoTurno.Cambiar_voto);
        	turnoService.save(turno);
        }
        	
        switch (turno.getEstadoTurno()) {
            case Principio_turno: {
                break;
            }
            case Elegir_rol: {
                if (ronda.getNumRonda() == 2) {
                    result = new ModelAndView();//TODO redirect JSP elegir roles consul
                }
                break;
            }
            case Esperar_voto:{
            	
            	if(turno.getEstadoTurno()==EstadoTurno.Esperar_voto) {
            		aviso="Esperando votos";
            	}
                if ((jugador.equals(turno.getEdil1()) || jugador.equals(turno.getEdil2())) && vototurno == null) {
                    result = new ModelAndView("redirect:/partida/juego/" + partidaId.toString()+"/votar");//TODO redirect JSP votar      
                }}
            case Cambiar_voto:{
            	
            	if(turno.getEstadoTurno()==EstadoTurno.Cambiar_voto) {
            		aviso="Esperando al Predor";
            	}
            	if (jugador.equals(turno.getPredor()) && turno.getEstadoTurno()==EstadoTurno.Cambiar_voto) {
            		result = new ModelAndView("redirect:/partida/juego/" + partidaId.toString()+"/cambiar");
            	}}
            case Votar_de_nuevo:
                break;
            case Contar_votos:
                break;
            case Elegir_faccion:{
                if (ronda.getNumRonda() == 2 || turno.getNumTurno() == partidaService.findJugadores(partidaId).size()) {
                    return new ModelAndView();//TODO redirect JSP elegir faccion
                }
                break;
            }
            case Terminar_turno: {
                partidaService.siguienteTurno(partidaId);
                break;
            }
            default:
                break;
        }
        
        
        
        

        result.addObject("partida", partidaService.findPartida(partidaId));
        result.addObject("jugador", jugador);
        result.addObject("turno", turno);
        result.addObject("ronda", ronda);
        result.addObject("faccion", faccion);
        result.addObject("votosleales", votosFavor);
        result.addObject("votostraidores", votosContra);
        result.addObject("mensajes", mensajes);
        result.addObject("aviso", aviso);
        result.addObject("temporizador", LocalTime.of(LocalTime.now().minusHours(partida.getFechaInicio().toLocalTime().getHour()).getHour(), LocalTime.now().minusMinutes(partida.getFechaInicio().toLocalTime().getMinute()).getMinute(),  LocalTime.now().minusSeconds(partida.getFechaInicio().toLocalTime().getSecond()).getSecond()));
        return result;
    }
    
    
    
    @GetMapping(value = "/juego/{partidaId}/votar")
    public ModelAndView GetVotar(@PathVariable("partidaId") Integer partidaId, HttpServletResponse response,@RequestParam(value="mensaje",required = false) String cuerpomensaje) throws Exception {
    	response.addHeader("Refresh", "5");

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	User currentUser = (User) authentication.getPrincipal();
    	Turno turno = partidaService.getTurnoActual(partidaId);
    	Ronda ronda = partidaService.getRondaActual(partidaId);
    	Partida partida = partidaService.findPartida(partidaId);
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
        Partida iniciada = partidaService.getPartidaIniciada(partida.getId());
        Faccion faccion = faccionService.getFaccionJugadorPartida(jugador.getId(),partidaId);
        VotosTurno vototurno = turnoService.conocerVoto(turno.getId(), jugador.getId());
        Integer votosFavor = partidaService.getVotosFavor(partidaId);
        Integer votosContra = partidaService.getVotosContra(partidaId);
        List<Mensaje> mensajes = mensajeService.getMensajesByPartidaId(partidaId);
        if(cuerpomensaje!=null) {
    		Mensaje nmensaje = new Mensaje();
            //nmensaje.setHora(LocalTime.now());
            nmensaje.setJugador(jugador);
            nmensaje.setPartida(partida);
            nmensaje.setTexto(cuerpomensaje);
            mensajeService.save(nmensaje);
    	}
    	if (iniciada == null) {
    		throw new Exception("Esta partida no ha sido iniciada");
    	}
    	ModelAndView votar = null;
    	if((jugador==turno.getEdil1() || jugador==turno.getEdil2()) && vototurno == null) {
        	 votar=new ModelAndView("/partidas/votar");
        	 }else {
            	 votar=new ModelAndView("redirect:/partida/juego/" + partida.getId().toString());
           
            }
    	votar.addObject("partida", partidaService.findPartida(partidaId));
    	votar.addObject("jugador", jugador);
        votar.addObject("turno", turno);
        votar.addObject("ronda", ronda);
        votar.addObject("mensajes", mensajes);
        votar.addObject("faccion", faccion);
        votar.addObject("votosleales", votosFavor);
        votar.addObject("votostraidores", votosContra);
        votar.addObject("faccion", faccion);
        votar.addObject("temporizador", LocalTime.of(LocalTime.now().minusHours(partida.getFechaInicio().toLocalTime().getHour()).getHour(), LocalTime.now().minusMinutes(partida.getFechaInicio().toLocalTime().getMinute()).getMinute(),  LocalTime.now().minusSeconds(partida.getFechaInicio().toLocalTime().getSecond()).getSecond()));
        
    	 return votar;
    }


                
    //TODO Cambiar todo esto para que sea un solo post
    @GetMapping(value="/juego/{partidaId}/votar/rojo")
    public String votacionRoja(@PathVariable("partidaId") Integer partidaId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
        
        Turno turno = partidaService.getTurnoActual(partidaId);

        try {
            turnoService.anadirVotoRojo(turno.getId(), jugador); 
        } catch(Exception e){
            System.out.println(e);
        }

        return "redirect:/partida/juego/"+partidaId.toString(); 
    	
    }
    
    @GetMapping(value="/juego/{partidaId}/votar/verde")
    public String votacionVerde(@PathVariable("partidaId") Integer partidaId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
        
        Turno turno = partidaService.getTurnoActual(partidaId);

        try {
            turnoService.anadirVotoVerde(turno.getId(), jugador); 
        } catch(Exception e){
            System.out.println(e);
        }

        return "redirect:/partida/juego/"+partidaId.toString(); 
    	
    }

    @GetMapping(value="/juego/{partidaId}/votar/amarillo")
    public ModelAndView votacionAmarillo(@PathVariable("partidaId") Integer partidaId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Jugador jugador = jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);

        Turno turno = partidaService.getTurnoActual(partidaId);

        try {
            turnoService.anadirVotoAmarillo(turno.getId(), jugador); 
        }catch(Exception e){
            System.out.println(e);
        }

        return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());    	
    
    }
    

}
