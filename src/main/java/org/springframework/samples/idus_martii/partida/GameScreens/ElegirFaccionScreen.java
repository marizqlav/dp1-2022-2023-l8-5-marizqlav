package org.springframework.samples.idus_martii.partida.GameScreens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ElegirFaccionScreen implements GameScreen {
	

    private FaccionService faccionService;
    private PartidaService partidaService;

    @Autowired
    ElegirFaccionScreen(@Lazy PartidaService partidaService, @Lazy FaccionService faccionService) {
        this.partidaService = partidaService;
        this.faccionService = faccionService;
    }

    @Override
    public String getAviso(Integer partidaId) {
        return "Elija facción";
    }

    @Override
    public ModelAndView getView(Integer partidaId, Jugador jugadorConectado) {
        Turno turno = partidaService.getTurnoActual(partidaId);
        Faccion faccion = faccionService.getFaccionJugadorPartida(turno.getConsul().getId(), partidaId);

        System.out.println("00000000000000000");
        if (jugadorConectado.equals(turno.getConsul()) && faccion.getFaccionSelecionada() == null) {
            
            ModelAndView result = new ModelAndView("/partidas/elegirFaccion");
            result.addObject("faccion", faccion);
            System.out.println("aaaaaaaaaaaaaaa");
            System.out.println(faccion);
            return result;
        }

        return new ModelAndView("/partidas/tablero");
    }
}
