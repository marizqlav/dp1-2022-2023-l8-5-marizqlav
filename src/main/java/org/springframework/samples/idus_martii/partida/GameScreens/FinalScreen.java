package org.springframework.samples.idus_martii.partida.GameScreens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class FinalScreen implements GameScreen {

    PartidaService partidaService;

    @Autowired
    FinalScreen(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @Override
    public String getAviso(Integer partidaId) {
        return "";
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {

        ModelAndView result = new ModelAndView("partidas/final");
        result.addObject("faccionGanadora", partidaService.getFaccionGanadora(partidaService.findPartida(partidaId)));
        result.addObject("jugadoresGanadores", partidaService.getJugadoresFromFaccionEnum(partidaService.findPartida(partidaId), 
            partidaService.getFaccionGanadora(partidaService.findPartida(partidaId))));
        return result;
    }
    
}
