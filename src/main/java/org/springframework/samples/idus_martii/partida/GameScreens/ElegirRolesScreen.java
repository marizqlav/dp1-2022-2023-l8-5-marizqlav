package org.springframework.samples.idus_martii.partida.GameScreens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ElegirRolesScreen implements GameScreen {

    private PartidaService partidaService;
    private TurnoService turnoService;

    @Autowired
    ElegirRolesScreen(@Lazy PartidaService partidaService, @Lazy TurnoService turnoService) {
        this.partidaService = partidaService;
        this.turnoService = turnoService;
    }

    @Override
    public String getAviso(Integer partidaId) {
        if (partidaService.getTurnoActual(partidaId).getPredor() == null) {
            return "Eligiendo al predor";
        } else
        if (partidaService.getTurnoActual(partidaId).getEdil1() == null) {
            return "Eligiendo al edil";
        } else {
            return "Eligiendo al otro edil";
        }
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {
        if (!jugadorConectado.equals(partidaService.getTurnoActual(partidaId).getConsul())) {
            return new ModelAndView("partidas/tablero");
        }
        
        ModelAndView result = new ModelAndView("/partidas/elegirRoles");

        if (partidaService.getTurnoActual(partidaId).getPredor() == null) {
            result.addObject("jugadores", turnoService.getJugadoresValidosParaRol(partidaService.getTurnoActual(partidaId).getId(), "predor"));
        } else
        if (partidaService.getTurnoActual(partidaId).getEdil1() == null || partidaService.getTurnoActual(partidaId).getEdil2() == null) {
            result.addObject("jugadores", turnoService.getJugadoresValidosParaRol(partidaService.getTurnoActual(partidaId).getId(), "edil"));
        }
        
        result.addObject("partida", partidaService.findPartida(partidaId));
        return result;
    }
    
}
