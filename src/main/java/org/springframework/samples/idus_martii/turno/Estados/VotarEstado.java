package org.springframework.samples.idus_martii.turno.Estados;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.VotarScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Component;

@Component
public class VotarEstado implements EstadoTurno {

    private VotarScreen votarScreen;

    private TurnoService turnoService;

    @Autowired
    VotarEstado(VotarScreen votarScreen, TurnoService turnoService) {

        this.votarScreen = votarScreen;

        this.turnoService = turnoService;
    }

    @Override
    public void takeAction(Turno context) {        
        
    }

    @Override
    public EstadoTurnoEnum getNextState(Turno turno) {

        if (turnoService.findVoto(turno.getId(), turno.getEdil1().getId()) != null && turnoService.findVoto(turno.getId(), turno.getEdil2().getId()) != null) {
        	return EstadoTurnoEnum.Espiar;
        }
        return EstadoTurnoEnum.Votar;
    }

    @Override
    public GameScreen getGameScreen() {
        return votarScreen;
    }
}
