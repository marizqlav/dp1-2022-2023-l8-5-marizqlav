package org.springframework.samples.idus_martii.jugador;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.samples.idus_martii.user.User;

import lombok.Setter;

@Service
public class JugadorService {
	private JugadorRepository jugadorRepo;
	
	@Autowired
	public JugadorService(JugadorRepository jugadorRepo) {
		this.jugadorRepo = jugadorRepo;
	}
	
	public List<Jugador> getAll(){
		return this.jugadorRepo.findAllJugadores();
	}
	public Jugador getJugadorById(int id) {
		return this.getJugadorById(id);
	}
	
	public User getUserByJugador(Jugador j) {
		User u = j.getUser();
		return this.jugadorRepo.findUserByJugador(u);
	}
	
}
