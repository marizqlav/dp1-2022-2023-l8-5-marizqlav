package org.springframework.samples.idus_martii.turno.Estados;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.faccion.FaccionService;

import org.springframework.samples.idus_martii.partida.GameScreens.ElegirFaccionScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;

import org.springframework.samples.idus_martii.turno.Turno;

import org.springframework.stereotype.Component;

@Component
@StatePattern.ConcreteState
public class ElegirFaccionEstado implements EstadoTurno {

    private ElegirFaccionScreen elegirFaccionScreen;

    private FaccionService faccionService;

    @Autowired
    ElegirFaccionEstado(ElegirFaccionScreen elegirFaccionScreen, FaccionService faccionService) {

        this.elegirFaccionScreen = elegirFaccionScreen;

        this.faccionService = faccionService;
    }

    @Override
    public void takeAction(Turno context) {        
        
    }

    @Override
    public EstadoTurnoEnum getNextState(Turno turno) {
    	
        if (faccionService.getFaccionJugadorPartida(turno.getConsul().getId(), turno.getRonda().getPartida().getId()).getFaccionSelecionada() != null) {
        	return EstadoTurnoEnum.TerminarTurno;
        }
        return EstadoTurnoEnum.ElegirFaccion;
    }

    @Override
    public GameScreen getGameScreen() {
        return elegirFaccionScreen;
    }
}
