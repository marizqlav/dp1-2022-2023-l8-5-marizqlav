package org.springframework.samples.idus_martii.turno.Estados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.partida.GameScreens.ElegirFaccionScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.VotarScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Component;

@Component
public class ElegirFaccion implements EstadoTurno {

    private ElegirFaccionScreen elegirFaccionScreen;

    private TurnoService turnoService;

    @Autowired
    ElegirFaccion(ElegirFaccionScreen elegirFaccionScreen, TurnoService turnoService) {

        this.elegirFaccionScreen = elegirFaccionScreen;

        this.turnoService = turnoService;
    }

    @Override
    public void takeAction(Turno context) {        
        
    }

    @Override
    public EstadoTurnoEnum getNextState(Turno turno) {
    	
        if (turnoService.findVoto(turno.getId(), turno.getEdil1().getId()) != null && turnoService.findVoto(turno.getId(), turno.getEdil2().getId()) != null) {
        	return EstadoTurnoEnum.TerminarTurno;
        }
        return EstadoTurnoEnum.ElegirFaccion;
    }

    @Override
    public GameScreen getGameScreen() {
        return elegirFaccionScreen;
    }
}
