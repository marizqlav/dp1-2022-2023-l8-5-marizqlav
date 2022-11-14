package org.springframework.samples.idus_martii.partida;

import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public PartidaService(PartidaRepository partidaRepo, TurnoService turnoService, RondaService rondaService) {
        this.partidaRepo = partidaRepo;
        this.turnoService = turnoService;
        this.rondaService = rondaService;
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
    
    public void IniciarPartida(Integer id) throws InitiationException {

        Partida partida = findPartida(id);
        
        //Restricciones
        if (partida.getRondas().size() > 0) {
            throw new InitiationException("No se puede iniciar una partida ya iniciada");
        }

        Ronda rondaInicial = new Ronda();
        rondaInicial.setPartida(partida);
        partida.getRondas().add(rondaInicial);
       
        Turno turnoInicial = new Turno();
        turnoInicial.setRonda(rondaInicial);
        rondaInicial.getTurnos().add(turnoInicial);
  
        List<Jugador> jugadores = findJugadores(id).stream()
        .sorted(Comparator.comparing(Jugador::getId))
        .collect(Collectors.toList());
        
        Function<Integer, Integer> addNumber = x -> (x == jugadores.size()) ? x + 1 : 0;

        Integer n =  (int) (Math.random() * (partida.getNumeroJugadores()));
        System.out.println(n);

        turnoInicial.setConsul(jugadores.get(n));
        n = addNumber.apply(n);
        System.out.println(n);

        turnoInicial.setPredor(jugadores.get(n));
        n = addNumber.apply(n);
        System.out.println(n);

        turnoInicial.setEdil1(jugadores.get(n));
        n = addNumber.apply(n);
        System.out.println(n);

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
		return partidaRepo.anadirLobby(idlobby,idpartida);
	}
    
    Integer anadirJugadorLobby(int idjugador, int idlobby) {
		return partidaRepo.anadirJugadorLobby(idjugador,idlobby);
	}
    
    Jugador estaJugadorLobby(int idjugador, int idlobby) {
		return partidaRepo.estaJugadorLobby(idjugador,idlobby);
	}
    
    Partida jugadorPartidaEnCurso(int idjugador) {
		return partidaRepo.jugadorPartidaEnCurso(idjugador);
	}
}