package org.springframework.samples.idus_martii.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.samples.idus_martii.statistics.StatisticsService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
	
	private final String STATISTICS_PLAYER_VIEW="/estadisticas/estadisticasJugador";
	private final StatisticsService statService;
	
	JugadorService jService;
	PartidaService pService;
	 
	@Autowired
	public StatisticsController(JugadorService jService, PartidaService pService, StatisticsService statService) {
		this.jService=jService;
		this.pService=pService;
		this.statService = statService;
	}
	 
	@Transactional(readOnly = true)
	@GetMapping("/jugador/{jugadorId}")
	public ModelAndView showStatisticPlayer(@PathVariable("jugadorId") Integer jugadorId ) {
		ModelAndView result=new ModelAndView(STATISTICS_PLAYER_VIEW);
		System.out.println(statService.paridasGanadas(jService.getJugadorById(jugadorId)));
		System.out.println(statService.partidasTotales(jService.getJugadorById(jugadorId)));
//	  	Jugador j = jService.getJugadorById(jugadorId);
//	    result.addObject("users", jService.getUserByJugador(j));
		return result;
	}

}
