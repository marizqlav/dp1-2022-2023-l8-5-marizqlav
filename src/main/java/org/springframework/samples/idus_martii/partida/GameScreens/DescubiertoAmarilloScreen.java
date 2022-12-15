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
public class DescubiertoAmarilloScreen implements GameScreen {

    private PartidaService partidaService;
    private TurnoService turnoService;

    @Autowired
    DescubiertoAmarilloScreen(@Lazy PartidaService partidaService, @Lazy TurnoService turnoService) {
        this.partidaService = partidaService;
        this.turnoService = turnoService;
    }

    @Override
    public String getAviso(Integer partidaId) {
        return "El voto era amarillo! El edil puede volver a votar";
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {
        Turno turno = partidaService.getTurnoActual(partidaId);
        VotosTurno votoTurno = turnoService.findVoto(turno.getId(), jugadorConectado.getId());

        Jugador edilEspiado = null;
        if (turnoService.findVoto(turno.getId(), turno.getEdil1().getId()).getEspiado()) {
            edilEspiado = turno.getEdil1();
        } else
        if (turnoService.findVoto(turno.getId(), turno.getEdil2().getId()).getEspiado()) {
            edilEspiado = turno.getEdil2();
        }

        if (jugadorConectado.equals(edilEspiado) && votoTurno.getVotoOriginal() == null) {
            return new ModelAndView("partidas/cambiar");
        }

        return new ModelAndView("/partidas/tablero");
    }
}
