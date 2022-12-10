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
public class RecuentoScreen implements GameScreen {

    private PartidaService partidaService;
    private TurnoService turnoService;

    @Autowired
    RecuentoScreen(@Lazy PartidaService partidaService, @Lazy TurnoService turnoService) {
        this.partidaService = partidaService;
        this.turnoService = turnoService;
    }


    @Override
    public String getAviso() {
        return "Estos son los votos finales del turno (no en orden)";
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {
        Turno turno = partidaService.getTurnoActual(partidaId);

        VotosTurno votoEdil1 = turnoService.findVoto(turno.getId(), turno.getEdil1().getId());
        VotosTurno votoEdil2 = turnoService.findVoto(turno.getId(), turno.getEdil2().getId());

        VotosTurno v1;
        VotosTurno v2;
        if (Math.random() > 0.5) {
            v1 = votoEdil1;
            v2 = votoEdil2;
        } else {
            v2 = votoEdil1;
            v1 = votoEdil2;
        }

        ModelAndView result = new ModelAndView("/partidas/recuento");

        result.addObject("votoEdil1", v1);
        result.addObject("votoEdil2", v2);

        return result;
    }
    
}
