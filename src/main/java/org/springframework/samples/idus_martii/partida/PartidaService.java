package org.springframework.samples.idus_martii.partida;

import java.util.List;
import java.util.Map;
import java.awt.Paint;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.EstadoTurno;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {
    
    private PartidaRepository partidaRepo;

    private RondaService rondaService;
    private TurnoService turnoService;
    private FaccionService faccionService;

    @Autowired
    public PartidaService(PartidaRepository partidaRepo, TurnoService turnoService, RondaService rondaService, 
        FaccionService faccionService) {
        this.partidaRepo = partidaRepo;
        this.turnoService = turnoService;
        this.rondaService = rondaService;
        this.faccionService = faccionService;
    }

    public void save(Partida partida) {
        partidaRepo.save(partida);
    }
    
    public void cancelarPartida(int id){
    	partidaRepo.deleteById(id);
    }
    
    public Partida findPartida(Integer id) {
        return partidaRepo.findById(id).get();
    }

    public List<Jugador> findJugadores(Integer partidaId) {
        return partidaRepo.findJugadores(partidaId);
    }
    
    public void IniciarPartida(Integer id, Lobby lobby) throws InitiationException {

        Partida partida = findPartida(id);

        //Restricciones
        if (partida.getFechaInicio() != null) {
            throw new InitiationException("No se puede iniciar una partida ya iniciada");
        } else if (lobby.getJugadores().size() > 8) {
            throw new InitiationException("Demasiados jugadores");
        } else if (lobby.getJugadores().size() < 5) {
            throw new InitiationException("No hay suficientes jugadores");
        }

    	partida.setFechaInicio(LocalDateTime.now());

        List<Jugador> jugadores = lobby.getJugadores();

        Ronda rondaInicial = new Ronda();
        rondaInicial.setNumRonda(1);
        rondaInicial.setPartida(partida);
        partida.getRondas().add(rondaInicial);
       
        Turno turnoInicial = new Turno();
        turnoInicial.setEstadoTurno(EstadoTurno.Elegir_rol);
        turnoInicial.setNumTurno(1);
        turnoInicial.setRonda(rondaInicial);
        rondaInicial.getTurnos().add(turnoInicial);
        
        Function<Integer, Integer> addNumber = x -> (x >= jugadores.size() - 1) ? 0 : x + 1;

        Integer n = (int) Math.floor((Math.random() * (partida.getNumeroJugadores())));

        turnoInicial.setConsul(jugadores.get(n));
        n = addNumber.apply(n);

        turnoInicial.setPredor(jugadores.get(n));
        n = addNumber.apply(n);

        turnoInicial.setEdil1(jugadores.get(n));
        n = addNumber.apply(n);

        turnoInicial.setEdil2(jugadores.get(n));
        
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

        save(partida);
    }
    
    public void rotarConsul(Integer partidaId) {
    	if(findPartida(partidaId).getRondas().get(-1).getNumRonda() == 2) {
        	List<Jugador> listaJugadores = partidaRepo.findJugadores(partidaId);
        	List<Jugador> listaJugadores2 = partidaRepo.findJugadores(partidaId);

            Function<Integer, Integer> addNumber = x -> (x >= listaJugadores.size() - 1) ? 0 : x + 1;
            
            Turno turno = getTurnoActual(partidaId);

            Integer posicionConsul = listaJugadores.indexOf(turno.getConsul());
            Integer n =  posicionConsul + 1;

            turno.setConsul(listaJugadores.get(n));
            listaJugadores2.remove(n);
            
            n=addNumber.apply(n);
            turnoService.save(turno);
    	}
    }

    
    public void rotarRoles(Integer partidaId) {
    	
    	List<Jugador> listaJugadores = partidaRepo.findJugadores(partidaId);
        
        Function<Integer, Integer> addNumber = x -> (x >= listaJugadores.size() - 1) ? 0 : x + 1;
        
        Turno turno = getTurnoActual(partidaId);

        Integer posicionConsul = listaJugadores.indexOf(turno.getConsul());
        Integer n =  posicionConsul + 1;

        turno.setConsul(listaJugadores.get(n));
        n = addNumber.apply(n);

        turno.setPredor(listaJugadores.get(n));
        n = addNumber.apply(n);

        turno.setEdil1(listaJugadores.get(n));
        n = addNumber.apply(n);

        turno.setEdil2(listaJugadores.get(n));
        n = addNumber.apply(n);
        
        turnoService.save(turno);
    }
        
    private void finalizarRonda(Integer partidaId) {
        Ronda ronda = getRondaActual(partidaId);

    	if (ronda.getNumRonda() == 1) {
    		iniciarRonda(ronda.getPartida().getId());
    	} else {
    		terminarPartida(ronda.getPartida());
    	}
    }
    
    
    public void siguienteTurno(Integer partidaId) {
        Turno turno = getTurnoActual(partidaId);

        if (turno.getNumTurno() == findJugadores(partidaId).size()) {
            finalizarRonda(partidaId);
        }
        iniciarTurno(partidaId);
    }

    private void iniciarRonda(Integer partidaId) { //Use instead of iniciarTurno for new Ronda
		Partida partida = findPartida(partidaId);

		Ronda ronda = new Ronda();
        ronda.setNumRonda(getRondaActual(partidaId).getNumRonda() + 1);
        ronda.setPartida(partida);
        
        rondaService.save(ronda);
	}

    public void iniciarTurno(Integer partidaId) {
        Turno turno = new Turno();
        turno.setNumTurno(getTurnoActual(partidaId).getNumTurno() + 1);
        turno.setRonda(getRondaActual(partidaId));

        turnoService.save(turno);
    }
         
	private void terminarPartida(Partida partida) {
		partida.setFechaFin(LocalDateTime.now());
		partida.actualizarVotos();
		int votosTotalesLeal = partida.getVotosLeales();
		int votosTotalesTraidor =  partida.getVotosTraidores();
		
		if(Math.max(votosTotalesLeal, votosTotalesTraidor)>partida.limite ) {
			int contadorLeales= 0;
			int contadorTraidores= 0;
			
			for(Faccion f: faccionService.getFaccionesPartida(partida.getId())) {
				if (f.getFaccionSelecionada() == FaccionesEnumerado.Leal) {
					contadorLeales= contadorLeales+1;
				} else if(f.getFaccionSelecionada() == FaccionesEnumerado.Traidor){
					contadorTraidores = contadorTraidores+1;
				}
			}
			if(contadorLeales == 0 || contadorTraidores == 0) {
				partida.setFaccionGanadora(FaccionesEnumerado.Mercader);
			} else if(votosTotalesLeal>votosTotalesTraidor) {
				partida.setFaccionGanadora(FaccionesEnumerado.Traidor);
			} else if(votosTotalesLeal<votosTotalesTraidor){
				partida.setFaccionGanadora(FaccionesEnumerado.Leal);
			} else {
				partida.setFaccionGanadora(FaccionesEnumerado.Mercader);
			}
		} else {
			if(Math.abs(votosTotalesLeal-votosTotalesTraidor) <= 1) {
				partida.setFaccionGanadora(FaccionesEnumerado.Mercader);
			} else if(votosTotalesLeal>votosTotalesTraidor) {
				partida.setFaccionGanadora(FaccionesEnumerado.Leal);
			} else {
				partida.setFaccionGanadora(FaccionesEnumerado.Traidor);
			}
		}
		
		
	}
	

    List<Partida> getPartidas() {
		return partidaRepo.findAll();
	}
    
    List<Partida> getPartidasEnJuego() {
		return partidaRepo.findAllEnJuego();
	}

    Partida getPartidaIniciada(int idpartida) {
		return partidaRepo.findPartidaIniciada(idpartida);
	}

    List<Partida> getPartidasFinalizadasJugador(int idjugador) {
		return partidaRepo.findAllFinalizadasJugador(idjugador);
	}

    Lobby getLobby(int idpartida) {
		return partidaRepo.getLobby(idpartida);
	}
    
    Integer anadirLobby(int idlobby, int idpartida) {
		return partidaRepo.anadirLobby(idlobby, idpartida);
	}
    
    Integer anadirJugadorLobby(int idjugador, int idlobby) {
		return partidaRepo.anadirJugadorLobby(idjugador, idlobby);
	}
    
    Jugador findJugadorInLobby(int idjugador, int idlobby) {
		return partidaRepo.findJugadorInLobby(idjugador, idlobby);
	}
    
    Partida jugadorPartidaEnCurso(int idjugador) {
		return partidaRepo.jugadorPartidaEnCurso(idjugador);
	}

    Turno getTurnoActual(Integer partidaId) {
    	Partida p = partidaRepo.findById(partidaId).get();
    	Ronda r = p.getRondas().get(p.getRondas().size()-1);
    	Turno t = r.getTurnos().get(r.getTurnos().size()-1);
    	return t;
    }
    
    Ronda getRondaActual(Integer partidaId) {
    	return partidaRepo.findById(partidaId).get().getRondas()
            .get(partidaRepo.findById(partidaId).get().getRondas().size()-1);

    }
    
    Integer getVotosFavor(Integer partidaId) {
    	return partidaRepo.getVotosFavor(partidaId).stream().mapToInt(Integer::intValue).sum();

    }
    
    Integer getVotosContra(Integer partidaId){
    	return partidaRepo.getVotosContra(partidaId).stream().mapToInt(Integer::intValue).sum();

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
}