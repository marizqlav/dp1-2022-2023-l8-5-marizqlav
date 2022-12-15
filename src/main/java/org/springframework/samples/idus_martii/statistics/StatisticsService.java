package org.springframework.samples.idus_martii.statistics;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.samples.idus_martii.partida.PartidaRepository;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
	
	private PartidaService partidaService;
	private PartidaRepository partidaRepo;
	
	@Autowired
	public StatisticsService(PartidaService partidaService, PartidaRepository partidaRepo){
		this.partidaRepo = partidaRepo;
		this.partidaService = partidaService;
	}
	
	public Map<FaccionesEnumerado, List<Integer>> paridasGanadas(Jugador jugador) {
		return partidaService.getStats(jugador);
	}
	
	public int partidasTotales(Jugador jugador) {
		return partidaRepo.findAllFinalizadasJugador(jugador.getId()).size();
	}
	

}
