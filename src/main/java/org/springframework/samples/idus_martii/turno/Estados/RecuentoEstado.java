package org.springframework.samples.idus_martii.turno.Estados;

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
public class RecuentoEstado implements EstadoTurno {

    private RecuentoScreen recuentoScreen;

    private TurnoService turnoService;

    @Autowired
    RecuentoEstado(@Lazy TurnoService turnoService, RecuentoScreen recuentoScreen) {
        this.turnoService = turnoService;

        this.recuentoScreen = recuentoScreen;
    }

    @Override
    public void takeAction(Turno context) {
        VotosTurno v1 = turnoService.findVoto(context.getId(), context.getEdil1().getId());
        VotosTurno v2 = turnoService.findVoto(context.getId(), context.getEdil2().getId());

        if (v1.getTipoVoto() == FaccionesEnumerado.Leal) {
            context.setVotosLeales(1);
        } else 
        if (v1.getTipoVoto() == FaccionesEnumerado.Traidor) {
            context.setVotosTraidores(1);
        } else 
        if (v1.getTipoVoto() == FaccionesEnumerado.Mercader) {
            context.setVotosNeutrales(1);
        }

        if (v2.getTipoVoto() == FaccionesEnumerado.Leal) {
            context.setVotosLeales(context.getVotosLeales() + 1);
        } else 
        if (v2.getTipoVoto() == FaccionesEnumerado.Traidor) {
            context.setVotosTraidores(context.getVotosTraidores() + 1);
        } else 
        if (v2.getTipoVoto() == FaccionesEnumerado.Mercader) {
            context.setVotosNeutrales(context.getVotosNeutrales() + 1);
        }
    }

    @Override
    public EstadoTurnoEnum getNextState(Turno context) {
        return EstadoTurnoEnum.Recuento;
    }

    @Override
    public GameScreen getGameScreen() {
        return recuentoScreen;
    }
    
}
