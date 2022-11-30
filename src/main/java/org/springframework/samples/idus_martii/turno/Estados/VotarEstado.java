package org.springframework.samples.idus_martii.turno.Estados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.VotarScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Component;

@Component
public class VotarEstado implements EstadoTurno {

    private PartidaService partidaService;
    private TurnoService turnoService;

    private VotarScreen votarScreen;

    private EspiarEstado espiarEstado;

    @Autowired
    VotarEstado(PartidaService partidaService, TurnoService turnoService, VotarScreen votarScreen, EspiarEstado espiarEstado) {
        this.partidaService = partidaService;
        this.turnoService = turnoService;

        this.votarScreen = votarScreen;

        this.espiarEstado = espiarEstado;
    }

    @Override
    public void takeAction(Turno context) {        
        
    }

    @Override
    public EstadoTurno getNextState(Turno turno) {

        if (turno.getVotosLeales() == 2 || turno.getVotosTraidores() == 2 || (turno.getVotosLeales() == 1 && turno.getVotosTraidores() == 1)) {
        	return espiarEstado;
        }
        return null;
    }

    @Override
    public GameScreen getGameScreen() {
        return votarScreen;
    }
}
