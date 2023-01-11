package org.springframework.samples.idus_martii.turno.Estados;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.idus_martii.partida.GameScreens.DefaultScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Component;

@Component
@StatePattern.ConcreteState
public class EmpezarTurnoEstado implements EstadoTurno {

    private DefaultScreen defaultScreen;
    private TurnoService turnoService;

    @Autowired
    EmpezarTurnoEstado(DefaultScreen defaultScreen, @Lazy TurnoService turnoService) {
        this.defaultScreen = defaultScreen;
        this.turnoService = turnoService;
    }

    @Override
    public void takeAction(Turno context) {

        for (Turno t : context.getRonda().getTurnos()) { //Bug fix
            System.err.println("----------------");
            if (!(t.equals(context)) && t.getConsul() == null) {
                turnoService.deleteTurnoById(t.getId());
            }
        }
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
