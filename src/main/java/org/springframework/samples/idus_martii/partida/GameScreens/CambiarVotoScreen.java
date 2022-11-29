package org.springframework.samples.idus_martii.partida.GameScreens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CambiarVotoScreen implements GameScreen {

    private PartidaService partidaService;

    @Autowired
    CambiarVotoScreen(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @Override
    public String getAviso() {
        return "Esperando al Predor";
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {
        Turno turno = partidaService.getTurnoActual(partidaId);

        if (jugadorConectado.equals(turno.getPredor())) {
            return new ModelAndView("redirect:/partida/juego/" + partidaId.toString() + "/espiar");
        }

        return new ModelAndView("redirect:/partida/juego/" + partidaId.toString());
    }
}
