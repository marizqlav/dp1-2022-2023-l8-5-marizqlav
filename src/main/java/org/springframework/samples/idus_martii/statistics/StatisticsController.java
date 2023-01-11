package org.springframework.samples.idus_martii.statistics;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
	
	private final String STATISTICS_PLAYER_VIEW="/estadisticas/estadisticasJugador";
	private final String STATISTICS_GLOBAL_VIEW="/estadisticas/estadisticasGlobales";	
	private final String STATISTICS_RANKING_VIEW="/estadisticas/ranking";
	
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
		Map<FaccionesEnumerado, List<Integer>> stats = statService.paridasGanadas(jService.getJugadorById(jugadorId));
		result.addObject("lealW",  stats.get(FaccionesEnumerado.Leal).get(0));
		result.addObject("traidorW",  stats.get(FaccionesEnumerado.Traidor).get(0));
		result.addObject("mercaderW",  stats.get(FaccionesEnumerado.Mercader).get(0));
		result.addObject("numPartidas",  statService.partidasTotales(jService.getJugadorById(jugadorId)));
		result.addObject("masJugada",  statService.faccionMasJugadaJugador(jService.getJugadorById(jugadorId)));
		result.addObject("jugador", jugadorId);
		return result;
	}
	
	@Transactional(readOnly = true)
	@GetMapping("/global")
	public ModelAndView showStatisticGlobal() {
		ModelAndView result=new ModelAndView(STATISTICS_GLOBAL_VIEW);
		result.addObject("max",statService.numJugadoresPartida().get("max"));
		result.addObject("min",statService.numJugadoresPartida().get("min"));
		result.addObject("media",statService.numJugadoresPartida().get("media"));
		result.addObject("mediaDuracion", pService.promedioPartida());
		result.addObject("totalPartidas",pService.getAllFinalizadas().size());
		result.addObject("partidas", pService.ultimas6partidas());
		result.addObject("larga", pService.partidaMasLarga());
		result.addObject("corta", pService.partidaMasCorta());
		result.addObject("faccionMasGanadora", pService.faccionMasGanadora());
		return result;
	}

	
	@GetMapping("/global/ranking")
	public ModelAndView showRanking() {
		ModelAndView result=new ModelAndView(STATISTICS_RANKING_VIEW);
		for(Jugador j : statService.getRanking().keySet()) {
			System.out.println(j.getUsername()
					+"Paridas" + statService.getRanking().get(j)[0]
					+"%" + statService.getRanking().get(j)[1]
							+"Score" + statService.getRanking().get(j)[2]);
			
		}
		result.addObject("ranking",statService.getRanking());
		result.addObject("players", statService.getRankingIndex());
		return result;
	}
}
