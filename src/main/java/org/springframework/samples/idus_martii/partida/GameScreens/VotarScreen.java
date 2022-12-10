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
public class VotarScreen implements GameScreen {

    private PartidaService partidaService;
    private TurnoService turnoService;

    @Autowired
    VotarScreen(@Lazy PartidaService partidaService, @Lazy TurnoService turnoService) {
        this.partidaService = partidaService;
        this.turnoService = turnoService;
    }

    @Override
    public String getAviso() {
        return "Esperando votos";
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {
        Turno turno = partidaService.getTurnoActual(partidaId);
        VotosTurno votoTurno = turnoService.findVoto(turno.getId(), jugadorConectado.getId());

        if ((jugadorConectado.equals(turno.getEdil1()) || jugadorConectado.equals(turno.getEdil2())) && votoTurno == null) {
            return new ModelAndView("partidas/votar"); //TODO mirar cambiar por el jsp en vez del redirect
        }

        return new ModelAndView("/partidas/tablero");
    }
    
}
