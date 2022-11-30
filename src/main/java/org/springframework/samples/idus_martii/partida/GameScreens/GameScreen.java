package org.springframework.samples.idus_martii.partida.GameScreens;

import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.web.servlet.ModelAndView;

public interface GameScreen {
    public String getAviso();
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado);
}
