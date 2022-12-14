package org.springframework.samples.idus_martii.turno.Estados;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.partida.GameScreens.DefaultScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Component;

@Component
public class EstablecerRolesEstado implements EstadoTurno {

    private PartidaService partidaService;
    private TurnoService turnoService;

    private GameScreen gameScreen;

    @Autowired
    EstablecerRolesEstado(PartidaService partidaService, TurnoService turnoService, DefaultScreen gameScreen) {
        this.partidaService = partidaService;
        this.turnoService = turnoService;

        this.gameScreen = gameScreen;
    }

    @Override
    public void takeAction(Turno turno) {
        Integer partidaId = turno.getRonda().getPartida().getId();
       
        if (turno.getNumTurno() == 1) {
            Integer random = (int) Math.floor((Math.random() * (partidaService.findPartida(partidaId).getNumeroJugadores())));
            setRolesConsecutivos(partidaId, random);
            System.out.println("Cambio 1 de roles");
        } else {
        	 System.out.println("Cambio siguiente");
        	 Turno turnoanterior = turno.getRonda().getTurnos().get(turno.getRonda().getTurnos().size()-2);
            setRolesConsecutivos(partidaId, partidaService.findJugadores(partidaId).indexOf(turnoanterior.getConsul()));
           
        }
    }

    public void setRolesConsecutivos(Integer partidaId, Integer posicionConsul) {
    	List<Jugador> listaJugadores = partidaService.findJugadores(partidaId);
        
        Function<Integer, Integer> addNumber = x -> (x >= listaJugadores.size() - 1) ? 0 : x + 1;
        
        Turno turno = partidaService.getTurnoActual(partidaId);
        System.out.println(turno);
        Integer n =  posicionConsul;
        System.out.println(n);
        
        n = addNumber.apply(n);
        turno.setConsul(listaJugadores.get(n));
        
        n = addNumber.apply(n);
        turno.setPredor(listaJugadores.get(n));
        
        n = addNumber.apply(n);
        turno.setEdil1(listaJugadores.get(n));

        n = addNumber.apply(n);
        turno.setEdil2(listaJugadores.get(n));
     

        turnoService.save(turno);
        System.out.println("Turno actualizado");
    }

    @Override
    public EstadoTurnoEnum getNextState(Turno context) {
        return EstadoTurnoEnum.Votar;
    }

    @Override
    public GameScreen getGameScreen() {
        return gameScreen;
    }
    
}
