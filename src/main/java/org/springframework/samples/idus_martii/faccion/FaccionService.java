package org.springframework.samples.idus_martii.faccion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaccionService {
	
	FaccionRepository repo;
	FaccionesConverter converter;
	
	@Autowired
	public FaccionService (FaccionRepository repo, FaccionesConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}
	
	public List<Faccion> getAllFacciones(){
		return repo.findAll();
	}
	
	public Faccion findFaccionById(int id) {
		return repo.findById(id);
	}
	
	public List<Jugador> getJugadoresPartida(Integer id) {
		return repo.getJugadoresPartida(id);
	}
	
	public Faccion save(Faccion f) {
		return repo.save(f);
	}
	@Transactional
	public Faccion getFaccionJugadorPartida(Integer idjugador, Integer idpartida) {
		return repo.getFaccionJugadorPartida(idjugador,idpartida);
	}
	
	public List<Faccion> getFaccionesPartida(Integer id){
		return repo.getFaccionesPartida(id);
	}
	
	@Transactional
	public void asignarFaccionAJugador(String faccion, Integer jugadorId, Integer partidaId) throws AccessException {
		FaccionesEnumerado f = converter.convert(faccion);
		Faccion faccionJugador = getFaccionJugadorPartida(jugadorId, partidaId);

		if (!(faccionJugador.getFaccionPosible1().equals(f) || faccionJugador.getFaccionPosible2().equals(f))) {
			throw new AccessException("La faccion asignada tiene que ser una de las posibles");
		} else if (faccionJugador.getFaccionSelecionada() != null) {
			throw new AccessException("No se puede asignar una facción a un jugador que ya tiene una");
		}

		faccionJugador.setFaccionSelecionada(f);
		repo.save(faccionJugador);
	}
	
	//TODO esto no sé que hace aquí, que el que lo ha hecho haga algo con ello o lo borro la proxima vez que lo vea
	// public int getVictoriasJugador(Jugador jugador) {
	// 	return 0;
	// }
}
