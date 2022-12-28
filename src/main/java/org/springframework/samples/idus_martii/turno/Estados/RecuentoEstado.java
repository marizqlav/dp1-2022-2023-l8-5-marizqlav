package org.springframework.samples.idus_martii.turno.Estados;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.RecuentoScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.samples.idus_martii.turno.VotosTurno;
import org.springframework.stereotype.Component;

@Component
@StatePattern.ConcreteState
public class RecuentoEstado implements EstadoTurno {

    private RecuentoScreen recuentoScreen;

    private TurnoService turnoService;

    private Integer counter = 0;
    private final Integer COUNT_LIMIT = 10;

    @Autowired
    RecuentoEstado(@Lazy TurnoService turnoService, RecuentoScreen recuentoScreen) {
        this.turnoService = turnoService;

        this.recuentoScreen = recuentoScreen;
    }

    @Override
    public void takeAction(Turno turno) {
        counter += 1;
        if (counter != 1) {
            return;
        }

        VotosTurno v1 = turnoService.findVoto(turno.getId(), turno.getEdil1().getId());
        VotosTurno v2 = turnoService.findVoto(turno.getId(), turno.getEdil2().getId());

        if (v1.getTipoVoto() == FaccionesEnumerado.Leal) {
            turno.setVotosLeales(1);
        } else 
        if (v1.getTipoVoto() == FaccionesEnumerado.Traidor) {
            turno.setVotosTraidores(1);
        } else 
        if (v1.getTipoVoto() == FaccionesEnumerado.Mercader) {
            turno.setVotosNeutrales(1);
        }

        if (v2.getTipoVoto() == FaccionesEnumerado.Leal) {
            turno.setVotosLeales(turno.getVotosLeales() + 1);
        } else 
        if (v2.getTipoVoto() == FaccionesEnumerado.Traidor) {
            turno.setVotosTraidores(turno.getVotosTraidores() + 1);
        } else 
        if (v2.getTipoVoto() == FaccionesEnumerado.Mercader) {
            turno.setVotosNeutrales(turno.getVotosNeutrales() + 1);
        }

        turnoService.save(turno);
    }

    @Override
    public EstadoTurnoEnum getNextState(Turno context) {
        if (counter > COUNT_LIMIT) {
        	counter = 0;
            return EstadoTurnoEnum.ElegirFaccion;
        }
        return EstadoTurnoEnum.Recuento;
    }

    @Override
    public GameScreen getGameScreen() {
        return recuentoScreen;
    }
    
}
