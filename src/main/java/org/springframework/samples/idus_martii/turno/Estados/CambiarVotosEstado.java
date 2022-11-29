package org.springframework.samples.idus_martii.turno.Estados;

import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.stereotype.Component;

@Component
public class CambiarVotosEstado implements EstadoTurno {

    @Override
    public void takeAction(Turno context) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public EstadoTurno getNextState(Turno context) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GameScreen getGameScreen() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
