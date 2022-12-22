package org.springframework.samples.idus_martii.turno.Estados;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.partida.GameScreens.DefaultScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.stereotype.Component;

@Component
@StatePattern.ConcreteState
public class EmpezarTurnoEstado implements EstadoTurno {

    private DefaultScreen defaultScreen;

    @Autowired
    EmpezarTurnoEstado(DefaultScreen defaultScreen) {
        this.defaultScreen = defaultScreen;
    }

    @Override
    public void takeAction(Turno context) {

    }

    @Override
    public EstadoTurnoEnum getNextState(Turno context) {

        if (context.getRonda().getNumRonda() == 1) {
            return EstadoTurnoEnum.EstablecerRoles;
        } else {
            return EstadoTurnoEnum.ElegirRoles;
        }
    }

    @Override
    public GameScreen getGameScreen() {
        return defaultScreen;
    }
    
}
