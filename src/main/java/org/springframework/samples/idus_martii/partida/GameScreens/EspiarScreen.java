package org.springframework.samples.idus_martii.partida.GameScreens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.turno.Turno;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class EspiarScreen implements GameScreen {

    private PartidaService partidaService;

    @Autowired
    EspiarScreen(@Lazy PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @Override
    public String getAviso(Integer partidaId) {
        return "Esperando al Predor";
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {
        Turno turno = partidaService.getTurnoActual(partidaId);

        if (jugadorConectado.equals(turno.getPredor())) {

            return new ModelAndView("/partidas/espiar");
        }
        return new ModelAndView("/partidas/tablero");
    }
}
