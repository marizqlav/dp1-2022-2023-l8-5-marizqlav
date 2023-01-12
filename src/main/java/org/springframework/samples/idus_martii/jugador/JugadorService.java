package org.springframework.samples.idus_martii.jugador;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.samples.idus_martii.user.AuthoritiesService;
import org.springframework.samples.idus_martii.user.User;
import org.springframework.samples.idus_martii.user.UserService;


@Service
public class JugadorService {
	
	private JugadorRepository jugadorRepo;
	
	private UserService userService;
	
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public JugadorService(JugadorRepository jugadorRepo, UserService userService, AuthoritiesService authoritiesService) {
		this.jugadorRepo = jugadorRepo;
		this.userService = userService;
		this.authoritiesService = authoritiesService;
	}
	@Transactional
	public List<Jugador> getAll(){
		return jugadorRepo.findAll();
	}
	@Transactional
	public List<List<Jugador>> getPlayersPaginated(Collection<Jugador> listaJugadores){
		List<List<Jugador>> jugadores = new ArrayList<List<Jugador>>();
		int maxJugadores = 0;
		int cont = 0;
		jugadores.add(new ArrayList<Jugador>());
		for(Jugador j : listaJugadores) {
			if(maxJugadores >1){
				maxJugadores = 0;
				cont = cont +1;
				jugadores.add(new ArrayList<Jugador>());
			}
			jugadores.get(cont).add(j);
			maxJugadores = maxJugadores +1;
		}
		return jugadores;
	}

	@Transactional
	public Jugador getByName(String name){
        return jugadorRepo.findByName(name);
    }
	@Transactional
	public Integer anadirAmigo(int idjugador, int idamigo){
        return jugadorRepo.anadirAmigo(idjugador, idamigo) ;
    }
	@Transactional
	public Integer sonAmigos(int idjugador, int idamigo){
		
        return jugadorRepo.sonAmigos(idjugador, idamigo) ;
    }
	@Transactional
	public Jugador getJugadorById(int id) {
		Jugador j=null;
		try{
			j= this.jugadorRepo.findById(id).get();
		}catch(Exception e) {
		}
		return j;
	}
	@Transactional
	public User getUserByJugador(Jugador j) {
		return this.jugadorRepo.findUserByJugador(j.getUser().getUsername());
	}
	@Transactional
	public List<Jugador> getJugadorByUsername(String username) {
		return this.jugadorRepo.findJugadorByUsername(username);
	}
	@Transactional
	public List<Jugador> getpeticionesAmistadJugador(Integer idjugador) {
		List<Integer> jugadoresId = this.jugadorRepo.findPeticionesAmistadJugador(idjugador);
		List<Jugador> salida = new ArrayList<>();
		for(Integer jugador:jugadoresId) {
			if(this.sonAmigos(jugador,idjugador)==null) {
				salida.add(this.getJugadorById(jugador));
			}
			
		}
		return salida;
	}
	@Transactional
	public List<Jugador> getAmigos(Integer idjugador) {
		List<Integer> jugadoresId = this.jugadorRepo.findPeticionesAmistadJugador(idjugador);
		List<Jugador> salida = new ArrayList<>();
		for(Integer jugador:jugadoresId) {
			if(this.sonAmigos(jugador,idjugador )!=null) {
				if(this.sonAmigos(idjugador,jugador)!=null)
				salida.add(this.getJugadorById(jugador));
			}
			
		}
		return salida;
	}
	@Transactional
	public void save(Jugador j) {
		this.jugadorRepo.save(j);
		this.userService.saveUser(j.getUser());
		this.authoritiesService.saveAuthorities(j.getUser().getUsername(), "player");;
		
		
	}
	@Transactional
	public Boolean noSonAmigos(int jugadorId, int amigo) {
		if( !(jugadorRepo.noSonAmigos(jugadorId, amigo) !=null || jugadorRepo.noSonAmigos(amigo, jugadorId) !=null) ) {
			return true;
		}else {
			return false;
		}
		
	}
	@Transactional
	public void rechazarPeticion(int jugadorId, int rechazadoId) {
		 this.jugadorRepo.rechazarPeticion(jugadorId, rechazadoId);
	}
	@Transactional
	public void deleteAmigo(int idjugador, int idamigo){
        this.jugadorRepo.rechazarPeticion(idjugador,idamigo);
        this.jugadorRepo.rechazarPeticion(idamigo,idjugador);
    }
	
	@Transactional
    public void deleteJugadorById(int id){
		Jugador jugadororiginal = getJugadorById(id);
		for(Jugador j:jugadororiginal.getSetAmigos()) {
			deleteAmigo(id,j.getId());
		}
		
        this.jugadorRepo.deleteById(id);
    }
	
}
