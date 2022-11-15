package org.springframework.samples.idus_martii.partida;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.ronda.RondaService;
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

    public Set<Jugador> findJugadores(Integer partidaId) {
        return partidaRepo.findJugadores(partidaId);
    }
    
    public void IniciarPartida(Integer id, Lobby lobby) throws InitiationException {

        Partida partida = findPartida(id);

        //Restricciones
        if (partida.getFechaInicio() != null) {
            throw new InitiationException("No se puede iniciar una partida ya iniciada");
        }

    	partida.setFechaInicio(LocalDateTime.now());

        List<Jugador> jugadores = lobby.getJugadores();

        Ronda rondaInicial = new Ronda();
        rondaInicial.setPartida(partida);
        partida.getRondas().add(rondaInicial);
       
        Turno turnoInicial = new Turno();
        turnoInicial.setRonda(rondaInicial);
        rondaInicial.getTurnos().add(turnoInicial);
  
        /*List<Jugador> jugadores = findJugadores(id).stream()
            .sorted(Comparator.comparing(Jugador::getId))
            .collect(Collectors.toList());*/

        List<FaccionesEnumerado> faccionesBag = new ArrayList<>();
        for (int i = 0; i < 2; i++) { faccionesBag.add(FaccionesEnumerado.Mercader); }
        for (int i = 0; i < jugadores.size() - 1; i++) { faccionesBag.add(FaccionesEnumerado.Leal); }
        for (int i = 0; i < jugadores.size() - 1; i++) { faccionesBag.add(FaccionesEnumerado.Traidor); }

        for (Jugador jugador : jugadores) {

            Faccion faccion = new Faccion();
            faccion.setJugador(jugador);
            faccion.setPartida(partida);
            faccion.setFaccionSelecionada(null);
            
            Integer r1 = (int) Math.random() * faccionesBag.size();
            FaccionesEnumerado f1 = faccionesBag.get(r1);
            faccionesBag.remove(f1);
            faccion.setFaccionPosible1(f1);

            Integer r2 = (int) Math.random() * faccionesBag.size();
            FaccionesEnumerado f2 = faccionesBag.get(r2);
            faccionesBag.remove(f2);
            faccion.setFaccionPosible2(f2);

            faccionService.save(faccion);
        }

        Function<Integer, Integer> addNumber = x -> (x == jugadores.size()) ? x + 1 : 0;

        Integer n =  (int) (Math.random() * (partida.getNumeroJugadores()));

        turnoInicial.setConsul(jugadores.get(n));
        n = addNumber.apply(n);

        turnoInicial.setPredor(jugadores.get(n));
        n = addNumber.apply(n);

        turnoInicial.setEdil1(jugadores.get(n));
        n = addNumber.apply(n);

        turnoInicial.setEdil2(jugadores.get(n));
        
        rondaService.save(rondaInicial);
        turnoService.save(turnoInicial);
        save(partida);
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
    
    
    Integer crearSufragium(int id, int idPartida, int max) {
        return partidaRepo.crearSufragium(id,idPartida, max);
    }
}