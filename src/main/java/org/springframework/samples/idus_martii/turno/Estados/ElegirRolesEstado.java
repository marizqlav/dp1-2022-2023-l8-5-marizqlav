package org.springframework.samples.idus_martii.turno.Estados;

import java.util.List;
import java.util.function.Function;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Component;

@Component
@StatePattern.ConcreteState
public class ElegirRolesEstado implements EstadoTurno {

    private PartidaService partidaService;
    private TurnoService turnoService;

    private boolean guard = true;

    @Autowired
    ElegirRolesEstado(PartidaService partidaService, TurnoService turnoService) {
        this.partidaService = partidaService;
        this.turnoService = turnoService;
    }

    @Override
    public void takeAction(Turno context) {
        if (!guard) {
            return;
        }

        Integer partidaId = context.getRonda().getPartida().getId();

    	List<Jugador> listaJugadores = partidaService.findJugadores(partidaId);
        
        Function<Integer, Integer> addNumber = x -> (x >= listaJugadores.size() - 1) ? 0 : x + 1;
        
        Turno turno = partidaService.getTurnoActual(partidaId);
        Turno turnoAnterior = turno.getRonda().getTurnos().get(turno.getRonda().getTurnos().size()-2);
        Integer n =  partidaService.findJugadores(partidaId).indexOf(turnoAnterior.getConsul());
        
        n = addNumber.apply(n);
        turno.setConsul(listaJugadores.get(n));

        turnoService.save(turno);

        guard = false;
    }

    @Override
    public EstadoTurnoEnum getNextState(Turno context) {
        if (context.getConsul() != null && context.getPredor() != null && context.getEdil1() != null && context.getEdil2() != null) {
            guard = true;
            return EstadoTurnoEnum.Votar;
        }
        return EstadoTurnoEnum.ElegirRoles;
    }

    @Override
    public GameScreen getGameScreen() {
        return null;
    }
    
}
