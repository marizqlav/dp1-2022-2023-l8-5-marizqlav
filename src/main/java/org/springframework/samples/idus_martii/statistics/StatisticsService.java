package org.springframework.samples.idus_martii.statistics;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.samples.idus_martii.partida.PartidaRepository;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
	
	private PartidaService partidaService;
	private JugadorService jugadorService;
	private PartidaRepository partidaRepo;
	
	@Autowired
	public StatisticsService(PartidaService partidaService, PartidaRepository partidaRepo, JugadorService jugadorService){
		this.partidaRepo = partidaRepo;
		this.partidaService = partidaService;
		this.jugadorService = jugadorService;
	}
	
	public Map<FaccionesEnumerado, List<Integer>> paridasGanadas(Jugador jugador) {
		return partidaService.getStats(jugador);
	}
	
	public int partidasTotales(Jugador jugador) {
		return partidaRepo.findAllFinalizadasJugador(jugador.getId()).size();
	}
	

	
	public FaccionesEnumerado faccionMasJugadaJugador (Jugador jugador){
		 return partidaService.faccionMasJugadaugador(jugador);
	}
  
	public Map<String, Duration> duracionPartidas(Jugador jugador){
		Map<String, Duration> stats = new HashMap<String, Duration>();
    	 Duration min = null;
    	 Duration max = null;
    	 Duration sum = null;
    	 for(Partida p : partidaRepo.findAllFinalizadasJugador(jugador.getId())) {
    		 Duration duration = Duration.between(p.getFechaCreacion(), p.getFechaFin());
    		 Duration durPartida = Duration.between(p.getFechaCreacion(), p.getFechaFin());
    		 stats.put(p.getId().toString(), durPartida);
    		 if(sum == null) {
    			 min = duration;
    	    	 max = duration;
    	    	 sum = duration;
    		 }
    		 else {
    			 if(duration.compareTo(min) <0) {
    				 min = duration;
    			 }
    			 else if(duration.compareTo(max) >0) {
    				 max = duration;
    			 }
    			 sum = sum.plus(duration);
    		 }
    	 }
    	 stats.put("max", max);
    	 stats.put("min", min);
    	 stats.put("media", sum.dividedBy(partidaRepo.findAllFinalizadasJugador(jugador.getId()).size()));
    	 return stats;
		
	}
	
	// ESTADÍSTICAS GLOBALES
	
	
	public Map<String, Integer> numJugadoresPartida(){
		Map<String, Integer> stats = new HashMap<String, Integer>();
	   	 int min = 5;
	   	 int max = 5;
	   	 int sum = 0;
	   	 for(Partida p : partidaRepo.findAllFinalizadas()) {
	   		 int jugadores = p.getNumeroJugadores();
	   		 stats.put(p.getId().toString(), jugadores);
	   		 if(sum == 0) {
	   			 min = jugadores;
	   	    	 max = jugadores;
	   	    	 sum = jugadores;
	   		 }
	   		 else {
	   			 if(jugadores<min) {
	   				 min = jugadores;
	   			 }
	   			 else if(jugadores>max) {
	   				 max = jugadores;
	   			 }
	   			 sum = sum + jugadores;
	   		 }
	   	 }
	   	 stats.put("max", max);
	   	 stats.put("min", min);
	   	 stats.put("media", sum/partidaRepo.findAllFinalizadas().size());
	   	 return stats;
	}
	
	 public Map<Jugador, Double[]> getRanking(){
    	 Map<Jugador, Double[]> ranking = new HashMap<Jugador, Double[]>();
    	 List<Double[]> scoresAndPLayer = new ArrayList<Double[]>();
    	 for(Jugador j : jugadorService.getAll()) {
    		 Double[] scores = {partidaService.getScore(j)[0],partidaService.getScore(j)[1],partidaService.getScore(j)[2],(double) j.getId()};
    		 scoresAndPLayer.add(scores);
    	 }
    	 for(Double[] d : scoresAndPLayer.stream().sorted((o1, o2) -> o1[2].compareTo(o2[2])).collect(Collectors.toList())) {
    		 int id = (int) Math.round(d[3]);
    		 Double[] stats = {d[0], d[1], d[2]};
    		 ranking.put(jugadorService.getJugadorById(id), stats);
    	 }
    	 
    	 return ranking;
    }
	 
	 public List<Jugador> getRankingIndex(){
		 List<Jugador> ranking = new ArrayList<Jugador>();
    	 List<Double[]> scoresAndPLayer = new ArrayList<Double[]>();
    	 for(Jugador j : jugadorService.getAll()) {
    		 Double[] scores = {partidaService.getScore(j)[0],partidaService.getScore(j)[1],partidaService.getScore(j)[2],(double) j.getId()};
    		 scoresAndPLayer.add(scores);
    	 }
    	 for(Double[] d : scoresAndPLayer.stream().sorted((o1, o2) -> o2[2].compareTo(o1[2])).collect(Collectors.toList())) {
    		 int id = (int) Math.round(d[3]);
    		 ranking.add(jugadorService.getJugadorById(id));
    	 }
    	 
    	 return ranking;
	 }
	

}
