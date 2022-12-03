package org.springframework.samples.idus_martii.partida;

import java.util.List;
import java.util.Map;
import java.awt.Paint;
import java.lang.reflect.Array;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.Exceptions.CancelException;
import org.springframework.samples.idus_martii.partida.Exceptions.CreationException;
import org.springframework.samples.idus_martii.partida.Exceptions.InitiationException;
import org.springframework.samples.idus_martii.partida.Exceptions.LobbyException;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.samples.idus_martii.turno.Estados.EstablecerRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstadoTurno;
import org.springframework.samples.idus_martii.turno.Estados.EstadoTurnoConverter;
import org.springframework.samples.idus_martii.turno.Estados.EstadoTurnoEnum;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {

    private final Integer MAX_PLAYERS_NUM = 8;
    private final Integer MIN_PLAYERS_NUM = 5;
    
    private PartidaRepository partidaRepo;

    private RondaService rondaService;
    private TurnoService turnoService;
    private FaccionService faccionService;

    private EstadoTurnoConverter estadoTurnoConverter;

    @Autowired
    public PartidaService(PartidaRepository partidaRepo, TurnoService turnoService, RondaService rondaService, 
        FaccionService faccionService, @Lazy EstadoTurnoConverter estadoTurnoConverter) {
        this.partidaRepo = partidaRepo;
        this.turnoService = turnoService;
        this.rondaService = rondaService;
        this.faccionService = faccionService;

        this.estadoTurnoConverter = estadoTurnoConverter;
    }

    
    public Partida findPartida(Integer id) {
        return partidaRepo.findById(id).get();
    }
    
    public List<Jugador> findJugadores(Integer partidaId) {
        return partidaRepo.findJugadores(partidaId);
    }
    
    List<Partida> getPartidas() {
        return partidaRepo.findAll();
    }
    
    List<Partida> getPartidasEnJuego() {
        return partidaRepo.findAllEnJuego();
    }

    List<Partida> getPartidasFinalizadasJugador(Integer idJugador) {
        return partidaRepo.findAllFinalizadasJugador(idJugador);
    }
    
    List<Partida> getAllPartidasFinalizadas() {
        return partidaRepo.findAllFinalizadas();
    }

    public List<Faccion> getJugadoresPartida(int id) {
  		return this.partidaRepo.findJugadoresPartida(id);
  	}
    
    public void crearPartida(Partida partida, Jugador jugador) throws CreationException {
        if (jugadorPartidaEnCurso(jugador.getId()) != null) {
            throw new CreationException("No se puede crear una partida si el jugador tiene una partida sin terminar");
        }
        partida.setFechaCreacion(LocalDateTime.now());
        partida.setJugador(jugador);
        partidaRepo.save(partida);
        createLobby(partida.getId());

       
    }
    
    public void cancelarPartida(Integer id) throws CancelException {
        if (findPartida(id).iniciada()) {
            throw new CancelException("No se puede cancelar una partida iniciada");
            //This ride only stops in an emergency. Crying is not an emergency.
        }
        partidaRepo.deleteById(id);
    }

    Lobby getLobby(Integer idpartida) {
		return partidaRepo.getLobby(idpartida);
	}
    
    Integer createLobby(Integer idpartida) {
		return partidaRepo.createLobby(idpartida, idpartida);
	}
    
    Integer addJugadorLobby(Integer idjugador, Integer idlobby) throws LobbyException {
        if (findJugadorInLobby(idjugador, idlobby) == null) {
            return partidaRepo.addJugadorLobby(idjugador, idlobby);
        } else {
            throw new LobbyException("El jugador ya está en el lobby");
        }
	}
    
    Jugador findJugadorInLobby(Integer idjugador, Integer idlobby) {
		return partidaRepo.findJugadorInLobby(idjugador, idlobby);
	}

    boolean checkLobbyFull(Integer partidaId) throws LobbyException {
        Partida partida = findPartida(partidaId);
        if (partida == null) {
            throw new LobbyException("Esta partida no existe");
        }

        if (partida.iniciada() || findJugadores(partidaId).size() == getLobby(partidaId).getJugadores().size()) {
            return true;
        } else {
            return false;
        }
    }
    
    Partida jugadorPartidaEnCurso(Integer idjugador) {
		return partidaRepo.jugadorPartidaEnCurso(idjugador);
	}

    public Turno getTurnoActual(Integer partidaId) {
    	Partida p = partidaRepo.findById(partidaId).get();
    	Ronda r = p.getRondas().get(p.getRondas().size()-1);
    	Turno t = r.getTurnos().get(r.getTurnos().size()-1);
    	return t;
    }
    
    public Ronda getRondaActual(Integer partidaId) {
    	return partidaRepo.findById(partidaId).get().getRondas()
            .get(partidaRepo.findById(partidaId).get().getRondas().size()-1);
    }
    
    public int getVictoriasJugador(Jugador jugador) {
		return partidaRepo.findPartidasGanadas(jugador.getId()).size();
	}
    
    public Map<FaccionesEnumerado, Integer> getStats(Jugador jugador){
    	List<Partida> victorias =  partidaRepo.findPartidasGanadas(jugador.getId());
    	Map<FaccionesEnumerado, Integer> stats = new HashMap<FaccionesEnumerado, Integer>();
    	for(FaccionesEnumerado faccion : FaccionesEnumerado.values()) {
    		stats.put(faccion, 0);
    	}
    	for(Partida p : victorias) {
    		stats.put(p.faccionGanadora, stats.get(p.faccionGanadora) +1);
    	}
    	return stats;
    }
    
    public Map<String, Duration> duracionPartidasJugador(Jugador jugador) {
    	 Map<String, Duration> stats = new HashMap<String, Duration>();
    	 Duration min = null;
    	 Duration max = null;
    	 Duration sum = null;
    	 for(Partida p : partidaRepo.findAllFinalizadasJugador(jugador.getId())) {
    		 Duration duration = Duration.between(p.getFechaCreacion(), p.getFechaFin());
    		 if(sum == null) {
    			 min = duration;
    	    	 max = duration;
    	    	 sum = duration;
    		 }
    		 else {
    			 if(duration.compareTo(min) <0) {
    				 min = duration;
    			 }
    			 else if(duration.compareTo(max) >0) {
    				 max = duration;
    			 }
    			 sum = sum.plus(duration);
    		 }
    	 }
    	 stats.put("max", max);
    	 stats.put("min", min);
    	 stats.put("media", sum.dividedBy(partidaRepo.findAllFinalizadasJugador(jugador.getId()).size()));
    	 return stats;
    }
    
    public Map<String, Integer> promedioJugadoresPartida(Jugador jugador){
   	 Map<String, Integer> stats = new HashMap<String, Integer>();
   	 int min = 5;
   	 int max = 5;
   	 int sum = 0;
   	 for(Partida p : partidaRepo.findAllFinalizadasJugador(jugador.getId())) {
   		 int jugadores = p.getNumeroJugadores();
   		 if(sum == 0) {
   			 min = jugadores;
   	    	 max = jugadores;
   	    	 sum = jugadores;
   		 }
   		 else {
   			 if(jugadores<min) {
   				 min = jugadores;
   			 }
   			 else if(jugadores>max) {
   				 max = jugadores;
   			 }
   			 sum = sum + jugadores;
   		 }
   	 }
   	 stats.put("max", max);
   	 stats.put("min", min);
   	 stats.put("media", sum/partidaRepo.findAllFinalizadasJugador(jugador.getId()).size());
   	 return stats;
   }

    public void iniciarPartida(Integer partidaId, Integer lobbyId) throws InitiationException {

        Partida partida = findPartida(partidaId);
        Lobby lobby = getLobby(lobbyId);

        //Restricciones
        if (partida.iniciada() == true) {
            throw new InitiationException("No se puede iniciar una partida ya iniciada");

        } else if (lobby.getJugadores().size() > MAX_PLAYERS_NUM) {
            throw new InitiationException("Demasiados jugadores");

        } else if (lobby.getJugadores().size() < MIN_PLAYERS_NUM) {
            throw new InitiationException("No hay suficientes jugadores");
        }

    	partida.setFechaInicio(LocalDateTime.now());

        List<Jugador> jugadores = lobby.getJugadores();

        Ronda rondaInicial = new Ronda();
        rondaInicial.setPartida(partida);
       
        Turno turnoInicial = new Turno();
        turnoInicial.setEstadoTurno(EstadoTurnoEnum.EstablecerRoles);
        turnoInicial.setRonda(rondaInicial);
        
        partidaRepo.save(partida);
        rondaService.save(rondaInicial);
        turnoService.save(turnoInicial);

        List<FaccionesEnumerado> faccionesBag = new ArrayList<>();
        for (int i = 0; i < 2; i++) { faccionesBag.add(FaccionesEnumerado.Mercader); }
        for (int i = 0; i < jugadores.size() - 1; i++) { faccionesBag.add(FaccionesEnumerado.Leal); }
        for (int i = 0; i < jugadores.size() - 1; i++) { faccionesBag.add(FaccionesEnumerado.Traidor); }

        for (Jugador jugador : jugadores) {

            Faccion faccion = new Faccion();
            faccion.setJugador(jugador);
            faccion.setPartida(partida);
            faccion.setFaccionSelecionada(null);
            
            Integer r1 = (int) Math.floor(Math.random() * faccionesBag.size());
            FaccionesEnumerado f1 = faccionesBag.get(r1);
            faccionesBag.remove(f1);
            faccion.setFaccionPosible1(f1);

            Integer r2 = (int) Math.floor(Math.random() * faccionesBag.size());
            FaccionesEnumerado f2 = faccionesBag.get(r2);
            faccionesBag.remove(f2);
            faccion.setFaccionPosible2(f2);

            faccionService.save(faccion);
        }

        partidaRepo.save(partida);
    }

    public void handleTurn(Integer partidaId) throws NotFoundException {
        Partida partida = findPartida(partidaId);

        if (partida == null || !partida.iniciada()) {
            throw new NotFoundException("No se encontró ninguna partida activa");
        }

        Turno turno = getTurnoActual(partidaId);

        EstadoTurno estado = estadoTurnoConverter.convert(turno.getEstadoTurno());

        estado.takeAction(turno);

        turno.setEstadoTurno(estado.getNextState(turno));
        turnoService.save(turno);
    }
    
    public GameScreen getCurrentGameScreen(Integer partidaId) {
        Turno turno = getTurnoActual(partidaId);
        return estadoTurnoConverter.convert(turno.getEstadoTurno()).getGameScreen();
    }
             
	public void terminarPartida(Partida partida) {
		partida.setFechaFin(LocalDateTime.now());
		int votosTotalesLeal = partida.getVotosLeales();
		int votosTotalesTraidor =  partida.getVotosTraidores();
		
		if (Math.max(votosTotalesLeal, votosTotalesTraidor) > partida.getLimite() ) {
			int contadorLeales = 0;
			int contadorTraidores = 0;
			
			for (Faccion f: faccionService.getFaccionesPartida(partida.getId())) {
				if (f.getFaccionSelecionada() == FaccionesEnumerado.Leal) {
					contadorLeales= contadorLeales + 1;
				} else if (f.getFaccionSelecionada() == FaccionesEnumerado.Traidor){
					contadorTraidores = contadorTraidores + 1;
				}
			}
			if(contadorLeales == 0 || contadorTraidores == 0) {
				partida.setFaccionGanadora(FaccionesEnumerado.Mercader);
			} else if (votosTotalesLeal > votosTotalesTraidor) {
				partida.setFaccionGanadora(FaccionesEnumerado.Traidor);
			} else if (votosTotalesLeal < votosTotalesTraidor){
				partida.setFaccionGanadora(FaccionesEnumerado.Leal);
			} else {
				partida.setFaccionGanadora(FaccionesEnumerado.Mercader);
			}
		} else {
			if (Math.abs(votosTotalesLeal - votosTotalesTraidor) <= 1) {
				partida.setFaccionGanadora(FaccionesEnumerado.Mercader);
			} else if (votosTotalesLeal > votosTotalesTraidor) {
				partida.setFaccionGanadora(FaccionesEnumerado.Leal);
			} else {
				partida.setFaccionGanadora(FaccionesEnumerado.Traidor);
			}
		}		
	}
}