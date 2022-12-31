package org.springframework.samples.idus_martii.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.stereotype.Service;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;

import java.util.Map;

@Service
public class AchievementService {
	
	AchievementRepository repo;
	PartidaService paridaService;
	
	@Autowired
	public AchievementService(AchievementRepository repo, PartidaService paridaService) {
		this.repo=repo;
		this.paridaService = paridaService;
	}
	
	List<Achievement> getAchievements(){
		return repo.findAll();
    }

	
	public Achievement getById(int id){
	   return repo.findById(id).get();
	}

	public void deleteAchievementById(int id){
		repo.deleteById(id);
	}

	public void save(Achievement achievement){
       repo.save(achievement);
    }
	
	public Map<FaccionesEnumerado, Integer> victoriasFaccion(Jugador j){
		 Map<FaccionesEnumerado, Integer> res = new HashMap<FaccionesEnumerado, Integer>();
		 Map<FaccionesEnumerado, List<Integer>> stats = paridaService.getStats(j);
		 for(FaccionesEnumerado facc : stats.keySet()) {
			 res.put(facc, stats.get(facc).get(0));
		 }
		 return res;
	}
	
	public List<Achievement> achievementsJugador(Jugador j){
		List<Achievement> res = new ArrayList<Achievement>();
		List<Integer> contLogro = new ArrayList<Integer>();
		contLogro.add(paridaService.getPartidasFinalizadasJugador(j.getId()).size());
		contLogro.add(paridaService.getVictoriasJugador(j));
		contLogro.add(paridaService.getStats(j).get(FaccionesEnumerado.Traidor).get(0));
		contLogro.add(paridaService.getStats(j).get(FaccionesEnumerado.Leal).get(0));
		contLogro.add(paridaService.getStats(j).get(FaccionesEnumerado.Mercader).get(0));
		contLogro.add(paridaService.ganadasSabotajeJugador(j));
		int cont = 0;
		for(Achievement a : repo.findAll()) {
			if(a.getThreshold()<=contLogro.get(cont)) {
				res.add(a);
			}
			cont = cont+1;
		}
		return res;
	}
	
	
}
