package org.springframework.samples.idus_martii.turno.Estados;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.idus_martii.partida.GameScreens.DescubiertoAmarilloScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Component;

@Component
@StatePattern.ConcreteState
public class DescubiertoAmarilloEstado implements EstadoTurno {

    private DescubiertoAmarilloScreen screen;

    private TurnoService turnoService;

    @Autowired
    DescubiertoAmarilloEstado(@Lazy TurnoService turnoService,DescubiertoAmarilloScreen screen) {
    	this.turnoService = turnoService;
        this.screen = screen;
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
        
        return EstadoTurnoEnum.DescubiertoAmarillo;
    }

    @Override
    public GameScreen getGameScreen() {
        return screen;
    }
    
}
