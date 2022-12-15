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
public class CambiarVotoScreen implements GameScreen {

    private PartidaService partidaService;
    private TurnoService turnoService;

    @Autowired
    CambiarVotoScreen(@Lazy PartidaService partidaService, @Lazy TurnoService turnoService) {
        this.partidaService = partidaService;
        this.turnoService = turnoService;
    }


    @Override
    public String getAviso(Integer partidaId) {
        return "El predor cambiará el voto";
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {
    
        Turno turno = partidaService.getTurnoActual(partidaId);
        
        if (jugadorConectado.equals(turno.getPredor())) {
            
            ModelAndView result = new ModelAndView("/partidas/cambiar");

            String segundaRonda = "false";
            if (turno.getRonda().getNumRonda() == 2) {
                segundaRonda = "true";
            }
            result.addObject("segundaRonda", segundaRonda);

            VotosTurno votoEdil1 = turnoService.findVoto(turno.getId(), turno.getEdil1().getId());
            VotosTurno votoEdil2 = turnoService.findVoto(turno.getId(), turno.getEdil2().getId());

            if (votoEdil1.getEspiado()) {
                result.addObject("votoEdil", votoEdil1);
            } else
            if (votoEdil2.getEspiado()) {
                result.addObject("votoEdil", votoEdil2);
            }

            return result;
        }

        return new ModelAndView("/partidas/tablero");
    }
    
}
