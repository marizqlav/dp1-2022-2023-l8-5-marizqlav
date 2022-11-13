package org.springframework.samples.idus_martii.jugador;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.samples.idus_martii.user.User;

@Service
public class JugadorService {
	private JugadorRepository jugadorRepo;
	
	@Autowired
	public JugadorService(JugadorRepository jugadorRepo) {
		this.jugadorRepo = jugadorRepo;
	}
	
	public List<Jugador> getAll(){
		return jugadorRepo.findAll();
	}

	
	public Jugador getByName(String name){
        return jugadorRepo.findByName(name);
    }
	
	public Integer anadirAmigo(int idjugador, int idamigo){
        return jugadorRepo.anadirAmigo(idjugador, idamigo);
    }

	public Jugador getJugadorById(int id) {
		return this.jugadorRepo.findById(id).get();
	}
	
	public User getUserByJugador(Jugador j) {
		return this.jugadorRepo.findUserByJugador(j.getUser().getUsername());
	}
	
	public List<Jugador> getJugadorByUsername(String username) {
		return this.jugadorRepo.findJugadorByUsername(username);
	}
	
	public Jugador save(Jugador j) {
		return this.jugadorRepo.save(j);
	}
	
}
