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

            if (votoEdil1.getEspiado() || votoEdil2.getEspiado()) {
                ModelAndView result = new ModelAndView("/partidas/cambiar");
                result.addObject("votoEdil", votoEdil1);

                String segundaRonda = "false";
                if (turno.getRonda().getNumRonda() == 2) {
                    segundaRonda = "true";
                }
                result.addObject("segundaRonda", segundaRonda);
                
                return result;
            } else
            {
                return new ModelAndView("/partidas/espiar");
            }
        }

        return new ModelAndView("/partidas/tablero");
    }
}
