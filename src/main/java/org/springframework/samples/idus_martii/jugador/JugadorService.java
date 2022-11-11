package org.springframework.samples.idus_martii.jugador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.ronda.Ronda;
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
		return jugadorRepo.findAll();
	}
	
	public Jugador getById(int id){
        return jugadorRepo.findById(id).get();
    }
	
	public Jugador getByName(String name){
        return jugadorRepo.findByName(name);
    }
	
	public Integer anadirAmigo(int idjugador, int idamigo){
        return jugadorRepo.anadirAmigo(idjugador, idamigo);
    }
}
