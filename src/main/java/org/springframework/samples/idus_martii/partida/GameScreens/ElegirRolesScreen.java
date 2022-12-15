package org.springframework.samples.idus_martii.partida.GameScreens;

import org.jpatterns.gof.CompositePattern.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ElegirRolesScreen implements GameScreen {

    private PartidaService partidaService;

    @Autowired
    ElegirRolesScreen(@Lazy PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @Override
    public String getAviso(Integer partidaId) {
        if (partidaService.getTurnoActual(partidaId).getPredor() == null) {
            return "Elige al predor";
        } else
        if (partidaService.getTurnoActual(partidaId).getEdil1() == null) {
            return "Elige al edil";
        } else {
            return "Elige al otro edil";
        }
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {
        return null; //TODO redirect al jsp apropiado
    }
    
}
