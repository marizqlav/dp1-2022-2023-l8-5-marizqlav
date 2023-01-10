package org.springframework.samples.idus_martii.turno.Estados;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.partida.GameScreens.DefaultScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Component;

@Component
@StatePattern.ConcreteState
public class TerminarTurnoEstado implements EstadoTurno {

    PartidaService partidaService;
    RondaService rondaService;
    TurnoService turnoService;

    DefaultScreen defaultScreen;

    @Autowired
    TerminarTurnoEstado(PartidaService partidaService, RondaService rondaService, TurnoService turnoService, DefaultScreen defaultScreen) {
        this.partidaService = partidaService;
        this.rondaService = rondaService;
        this.turnoService = turnoService;

        this.defaultScreen = defaultScreen;
    }

    @Override
    public void takeAction(Turno context) {
        siguienteTurno(context.getRonda().getPartida().getId());
    }

    public void siguienteTurno(Integer partidaId) {
        Turno turno = partidaService.getTurnoActual(partidaId);
        if (turno.getNumTurno() == partidaService.findJugadores(partidaId).size()) {
            finalizarRonda(partidaId);
        }

        iniciarTurno(partidaId);
    }

    private void finalizarRonda(Integer partidaId) {
        Ronda ronda = partidaService.getRondaActual(partidaId);
    	if (ronda.getNumRonda() == 1) {
    		iniciarRonda(ronda.getPartida().getId());
    	}
    }

    public void iniciarTurno(Integer partidaId) {
        Turno turno = new Turno();
        turno.setRonda(partidaService.getRondaActual(partidaId));
        Ronda ronda = partidaService.getRondaActual(partidaId);
        ronda.getTurnos().add(turno);

        turnoService.save(turno);
        rondaService.save(ronda);
    }

    private void iniciarRonda(Integer partidaId) { //Use instead of iniciarTurno for new Ronda
		Partida partida = partidaService.findPartida(partidaId);
		
		Ronda ronda = new Ronda();
        ronda.setPartida(partida);
        
        rondaService.save(ronda);
        partida.getRondas().add(ronda);
        partidaService.save(partida);

	}

    @Override
    public EstadoTurnoEnum getNextState(Turno context) {
        return null;
    }

    @Override
    public GameScreen getGameScreen() {
        return defaultScreen;
    }
    
}
