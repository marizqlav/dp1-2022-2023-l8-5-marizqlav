package org.springframework.samples.idus_martii.partida.GameScreens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.samples.idus_martii.turno.VotosTurno;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class EspiarScreen implements GameScreen {

    private PartidaService partidaService;
    private TurnoService turnoService;

    @Autowired
    EspiarScreen(@Lazy PartidaService partidaService, @Lazy TurnoService turnoService) {
        this.partidaService = partidaService;
        this.turnoService = turnoService;
    }

    @Override
    public String getAviso() {
        return "Esperando al Predor";
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {
        Turno turno = partidaService.getTurnoActual(partidaId);

        if (jugadorConectado.equals(turno.getPredor())) {
            VotosTurno votoEdil1 = turnoService.findVoto(turno.getId(), turno.getEdil1().getId());
            VotosTurno votoEdil2 = turnoService.findVoto(turno.getId(), turno.getEdil2().getId());

            ModelAndView result = new ModelAndView("/partidas/espiar");

            result.addObject("votoEdil1", votoEdil1);
            result.addObject("votoEdil2", votoEdil2);

            return result;
        }

        return new ModelAndView("/partidas/tablero");
    }
}
