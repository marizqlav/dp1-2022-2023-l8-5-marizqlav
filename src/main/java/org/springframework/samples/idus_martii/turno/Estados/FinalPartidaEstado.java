package org.springframework.samples.idus_martii.turno.Estados;

import java.time.LocalDateTime;
import java.util.List;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.partida.GameScreens.FinalScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.stereotype.Component;

@Component
@StatePattern.ConcreteState
public class FinalPartidaEstado implements EstadoTurno {

    private FinalScreen screen;
    private PartidaService partidaService;

    @Autowired
    FinalPartidaEstado(FinalScreen screen, @Lazy PartidaService partidaService) {
        this.screen = screen;
        this.partidaService = partidaService;
    }

    @Override
    public void takeAction(Turno context) {
        Partida partida = context.getRonda().getPartida();
        if (partida.finalizada()) {
            return;
        }
        partida.setFechaFin(LocalDateTime.now());
        partida.setFaccionGanadora(partidaService.getFaccionGanadora(partida.getId()));
        partidaService.save(partida);
    }

    @Override
    public EstadoTurnoEnum getNextState(Turno context) {
        return EstadoTurnoEnum.FinalPartida;
    }

    @Override
    public GameScreen getGameScreen() {
        return screen;
    }
    
}
