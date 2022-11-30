package org.springframework.samples.idus_martii.turno.Estados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.partida.GameScreens.EspiarScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.stereotype.Component;

@Component
public class EspiarEstado implements EstadoTurno {

    private EspiarScreen espiarScreen;

    @Autowired
    EspiarEstado(EspiarScreen espiarScreen) {
        this.espiarScreen = espiarScreen;
    }

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
        return espiarScreen;
    }
    
}
