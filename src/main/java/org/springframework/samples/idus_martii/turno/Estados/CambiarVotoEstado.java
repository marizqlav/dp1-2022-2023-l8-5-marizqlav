package org.springframework.samples.idus_martii.turno.Estados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.idus_martii.partida.GameScreens.CambiarVotoScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Component;

@Component
public class CambiarVotoEstado implements EstadoTurno {

    private CambiarVotoScreen cambiarVotoScreen;

    private TurnoService turnoService;

    @Autowired
    CambiarVotoEstado(@Lazy TurnoService turnoService, CambiarVotoScreen cambiarVotoScreen) {
        this.turnoService = turnoService;
        this.cambiarVotoScreen = cambiarVotoScreen;
    }

    @Override
    public void takeAction(Turno context) {
        
    }

    @Override
    public EstadoTurnoEnum getNextState(Turno context) {

        if (turnoService.findVoto(context.getId(), context.getEdil1().getId()).getVotoOriginal() != null ||
            turnoService.findVoto(context.getId(), context.getEdil2().getId()).getVotoOriginal() != null) {
            
            return EstadoTurnoEnum.Recuento;
        }

        return EstadoTurnoEnum.CambiarVoto;
    }

    @Override
    public GameScreen getGameScreen() {

        return cambiarVotoScreen;
    }
    
}
