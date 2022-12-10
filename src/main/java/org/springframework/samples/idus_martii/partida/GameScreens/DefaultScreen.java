package org.springframework.samples.idus_martii.partida.GameScreens;

import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class DefaultScreen implements GameScreen {

    @Override
    public String getAviso() {
        return "";
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {
        return new ModelAndView("/partidas/tablero");
    }
    
}
