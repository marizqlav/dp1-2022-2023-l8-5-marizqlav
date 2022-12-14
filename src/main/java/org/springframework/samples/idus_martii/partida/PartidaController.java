package org.springframework.samples.idus_martii.partida;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.mensaje.Mensaje;
import org.springframework.samples.idus_martii.mensaje.MensajeService;
import org.springframework.samples.idus_martii.partida.Exceptions.CancelException;
import org.springframework.samples.idus_martii.partida.Exceptions.CreationException;
import org.springframework.samples.idus_martii.partida.Exceptions.InitiationException;
import org.springframework.samples.idus_martii.partida.Exceptions.LobbyException;
import org.springframework.samples.idus_martii.partida.GameScreens.DefaultScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.samples.idus_martii.turno.VotosTurno;
import org.springframework.samples.idus_martii.turno.Estados.EstadoTurno;
import org.springframework.security.acls.model.NotFoundException;
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
	private final String PARTIDAS_LISTING_VIEW_CREADAS ="/partidas/partidasListCreadas";
    private final String PARTIDAS_LISTING_VIEW_ACTUALES = "/partidas/partidasListActuales";
	private final String  PARTIDAS_DISPONIBLES_LISTING_VIEW="/partidas/partidasDisponiblesList";
	private final String  PARTIDA_DETAIL_VIEW="/partidas/partidaDetail";
	private final String  LOBBY_ESPERA_VIEW="/partidas/lobbyEspera";

    private final String MENU_REFRESH_TIME="5";
    private final String GAME_REFRESH_TIME="60";

    PartidaService partidaService;
    JugadorService jugadorService;
    RondaService rondaService;
    TurnoService turnoService;
    FaccionService faccionService;
    MensajeService mensajeService;

    @Autowired
    public PartidaController(PartidaService partidaService, JugadorService jugadorService, RondaService rondaService, 
        TurnoService turnoService, FaccionService faccionService, MensajeService mensajeService) {
        this.partidaService = partidaService;
        this.jugadorService = jugadorService;
        this.rondaService = rondaService;
        this.turnoService = turnoService;
        this.faccionService = faccionService;
        this.mensajeService = mensajeService;
    }

    private Jugador getJugadorConectado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return jugadorService.getJugadorByUsername(currentUser.getUsername()).get(0);
    }
    
    private User getUsuarioConectado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return currentUser;
    }
    
    @Transactional(readOnly = true)
	 @GetMapping("/{id}/detalles")
	 public ModelAndView showDetallesPartida(@PathVariable int id){
    	ModelAndView result=new ModelAndView(PARTIDA_DETAIL_VIEW);
    	result.addObject("partida", partidaService.findPartida(id));
    	result.addObject("datospartida", partidaService.getJugadoresPartida(id));
	        return result;
	    }
    
    @Transactional(readOnly = true)
    @GetMapping("/disponibles")
    public ModelAndView showPartidasDisponibles(HttpServletResponse response){
    	response.addHeader("Refresh", MENU_REFRESH_TIME);

        ModelAndView result = new ModelAndView(PARTIDAS_DISPONIBLES_LISTING_VIEW);
        result.addObject("partidas", partidaService.getPartidasEnJuego());

        return result;
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/creadas")
    public ModelAndView showPartidasCreadas(HttpServletResponse response) {
        response.addHeader("Refresh", MENU_REFRESH_TIME);

        ModelAndView result = new ModelAndView(PARTIDAS_LISTING_VIEW_CREADAS);
        Jugador jugador = getJugadorConectado();
  
        	result.addObject("partidas", partidaService.getPartidasCreadasJugador(jugador.getId()));
       
        return result;
    }

    @Transactional(readOnly = true)
    @GetMapping("/finalizadas")
    public ModelAndView showPartidasFinalizadas(HttpServletResponse response) {
        response.addHeader("Refresh", MENU_REFRESH_TIME);

        ModelAndView result = new ModelAndView(PARTIDAS_LISTING_VIEW_FINALIZADAS);
        System.out.println(getUsuarioConectado().getAuthorities().toString());
        if(getUsuarioConectado().getAuthorities().toString().equals("[admin]")) {
        	result.addObject("partidas", partidaService.getAllPartidasFinalizadas());
        }else {
        result.addObject("partidas", partidaService.getPartidasFinalizadasJugador(getJugadorConectado().getId()));
        }
        return result;
    }

    @Transactional(readOnly = true)
    @GetMapping("/enJuego")
    public ModelAndView showPartidasEnJuego(HttpServletResponse response){
    	response.addHeader("Refresh", MENU_REFRESH_TIME);
        ModelAndView result = new ModelAndView(PARTIDAS_LISTING_VIEW_ACTUALES);

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
    public ModelAndView processCreationForm(@Valid Partida partida, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(VIEWS_PARTIDA_CREATE_OR_UPDATE_FORM);
        }
        else {
            Jugador jugador = getJugadorConectado();
            try {
                this.partidaService.crearPartida(partida, jugador);
            } catch (CreationException e) {
                ModelAndView res = new ModelAndView(VIEWS_PARTIDA_CREATE_OR_UPDATE_FORM);
                res.addObject("message", "No se pudo crear la partida");
                return res;
            }    
            return new ModelAndView("redirect:/partida/" + partida.getId().toString());
        }

    }
	
	@GetMapping(value = "/{partidaId}/cancelar")
    public ModelAndView cancelarPartida(@PathVariable("partidaId") Integer partidaId) {        
    	try {
            partidaService.cancelarPartida(partidaId);
        } catch (CancelException e) {
            ModelAndView result = new ModelAndView("redirect:/");
            result.addObject("message", "No se puede cancelar la partida");
            return result;
        }
        ModelAndView result = new ModelAndView("welcome");
        result.addObject("message", "Se ha cancelado la partida correctamente");
        return result;
    }
	
    @GetMapping(value = "/{partidaId}")
    public ModelAndView lobby(@PathVariable("partidaId") Integer partidaId, HttpServletResponse response) {
    	response.addHeader("Refresh", MENU_REFRESH_TIME);
        
        Jugador jugador = getJugadorConectado();

        try {
            partidaService.addJugadorLobby(jugador.getId(), partidaId);
        } catch (LobbyException e) { }

        try {
            if (partidaService.checkLobbyFull(partidaId)) {

                ModelAndView result = new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
            	return result;
            } else {

                ModelAndView result = new ModelAndView(LOBBY_ESPERA_VIEW);
                result.addObject("jugadores", partidaService.getLobby(partidaId).getJugadores());
                result.addObject("partida", partidaService.findPartida(partidaId));
                return result;
            }
            
        } catch (LobbyException e) {

            ModelAndView result = new ModelAndView("redirect:/");
            result.addObject("message", "Esta partida no existe");
            return result;
        }
	}
   
    @GetMapping(value = "/juego/{partidaId}/iniciar")
    public ModelAndView IniciarPartida(@PathVariable("partidaId") Integer partidaId) {
        try {
            partidaService.iniciarPartida(partidaId, partidaId);

        } catch (InitiationException e) {
            ModelAndView res = new ModelAndView("redirect:/partida/" + partidaId.toString());
            res.addObject("message", "Error al iniciar");
            return res;
        }

        return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
    }
    
    @GetMapping(value = "/juego/{partidaId}")
    public ModelAndView GetPartidaGeneral(@PathVariable("partidaId") Integer partidaId, HttpServletResponse response) {
    	response.addHeader("Refresh", GAME_REFRESH_TIME);

        Jugador jugador = getJugadorConectado();

        //List<Mensaje> mensajes = mensajeService.getMensajesByPartidaId(partidaId);

    	Turno turno = partidaService.getTurnoActual(partidaId);
    	Ronda ronda = partidaService.getRondaActual(partidaId);
    	Partida partida = partidaService.findPartida(partidaId);

        try {
            partidaService.handleTurn(partidaId);
        } catch (NotFoundException e) {
            ModelAndView res = new ModelAndView("welcome");
            res.addObject("message", "No se encontró ninguna partida");
            return res;
        }

        GameScreen gameScreen = partidaService.getCurrentGameScreen(partidaId);
        ModelAndView result = gameScreen.getView(partidaId, jugador);
        String aviso = gameScreen.getAviso();
        
        Integer votosFavor = partida.getVotosLeales();
        Integer votosContra = partida.getVotosTraidores();
        Faccion faccion = faccionService.getFaccionJugadorPartida(jugador.getId(), partidaId);
        
        result.addObject("partida", partidaService.findPartida(partidaId));
        result.addObject("jugador", jugador);
        result.addObject("turno", turno);
        result.addObject("ronda", ronda);
        result.addObject("faccion", faccion);
        result.addObject("votosleales", votosFavor);
        result.addObject("votostraidores", votosContra);
        //result.addObject("mensajes", mensajes);
        result.addObject("aviso", aviso);
        result.addObject("temporizador", LocalTime.of(LocalTime.now().minusHours(partida.getFechaInicio().toLocalTime().getHour()).getHour(), LocalTime.now().minusMinutes(partida.getFechaInicio().toLocalTime().getMinute()).getMinute(),  LocalTime.now().minusSeconds(partida.getFechaInicio().toLocalTime().getSecond()).getSecond()));
        return result;
    }

    @GetMapping(value = "/juego/{partidaId}/espiar")
    public ModelAndView GetEspiarEdil(@PathVariable("partidaId") Integer partidaId, @RequestParam String voto) {
        
        try {
            turnoService.espiarVoto(partidaId, getJugadorConectado(), voto);
        } catch (AccessException e) { }
        
        return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
    }
    
    @GetMapping(value="/juego/{partidaId}/cambiar")
    public ModelAndView cambiarvoto(@PathVariable("partidaId") Integer partidaId, @RequestParam String voto) {

        Turno turno = partidaService.getTurnoActual(partidaId);

        try {
            if (turnoService.findVoto(turno.getId(), turno.getEdil1().getId()).getEspiado()) {
                turnoService.cambiarVoto(turno.getId(), getJugadorConectado(), turno.getEdil1().getId(), voto);
            } else
            if (turnoService.findVoto(turno.getId(), turno.getEdil2().getId()).getEspiado()) {
                
                turnoService.cambiarVoto(turno.getId(), getJugadorConectado(), turno.getEdil2().getId(), voto);
            }
        } catch (AccessException e) { }
        
        return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
    }
    
    @GetMapping(value="/juego/{partidaId}/votar")
    public ModelAndView votacionRoja(@PathVariable("partidaId") Integer partidaId, @RequestParam String color) {
        
        Jugador jugador = getJugadorConectado();
        Turno turno = partidaService.getTurnoActual(partidaId);

        try {
            turnoService.anadirVoto(turno.getId(), jugador, color);
        } catch(AccessException e) { }

        return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
    }
}
