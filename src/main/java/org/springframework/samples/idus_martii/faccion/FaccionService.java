package org.springframework.samples.idus_martii.faccion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.stereotype.Service;

@Service
public class FaccionService {
	
	FaccionRepository repo;
	
	@Autowired
	public FaccionService (FaccionRepository repo) {
		this.repo = repo;
	}
	
	public List<Faccion> getAllFacciones(){
		return repo.findAll();
	}
	
	public Faccion findFaccionById(int id) {
		return repo.findById(id);
	}
	
	public List<Jugador> getJugadoresPartida(int id) {
		return repo.getJugadoresPartida(id);
	}
	
	public Faccion save(Faccion f) {
		return repo.save(f);
	}
	
	public Faccion getFaccionJugadorPartida(int idjugador, int idpartida) {
		return repo.getFaccionJugadorPartida(idjugador,idpartida);
	}
	
	public void  setFaccionSelecionada(int jugadorid, int partidaid, String faccionElegida) {
		 repo.setFaccionSelecionada(jugadorid, partidaid, faccionElegida);
	}
	
	
	public List<Faccion> getFaccionesPartida(int id){
		return repo.getFaccionesPartida(id);
	}
}
