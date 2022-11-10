package org.springframework.samples.idus_martii.jugador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
