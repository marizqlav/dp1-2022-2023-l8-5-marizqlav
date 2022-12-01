package org.springframework.samples.idus_martii.jugador;

import java.util.ArrayList;
import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.samples.idus_martii.user.Authorities;
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
	
	public List<Jugador> getAll(){
		return jugadorRepo.findAll();
	}

	
	public Jugador getByName(String name){
        return jugadorRepo.findByName(name);
    }
	
	public Integer anadirAmigo(int idjugador, int idamigo){
        return jugadorRepo.anadirAmigo(idjugador, idamigo) ;
    }
	
	public Integer sonAmigos(int idjugador, int idamigo){
		
        return jugadorRepo.sonAmigos(idjugador, idamigo) ;
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
	
	public List<Jugador> getpeticionesAmistadJugador(Integer idjugador) {
		List<Integer> jugadoresId = this.jugadorRepo.findPeticionesAmistadJugador(idjugador);
		List<Jugador> salida = new ArrayList<>();
		for(Integer jugador:jugadoresId) {
			if(this.sonAmigos(jugador,idjugador )==null) {
				salida.add(this.getJugadorById(jugador));
			}
			
		}
		return salida;
	}
	
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
	
	public void save(Jugador j) {
		this.jugadorRepo.save(j);
		this.userService.saveUser(j.getUser());
		this.authoritiesService.saveAuthorities(j.getUser().getUsername(), "player");;
		
		
	}
	
	public Boolean noSonAmigos(int jugadorId, int amigo) {
		if( !(jugadorRepo.noSonAmigos(jugadorId, amigo) !=null || jugadorRepo.noSonAmigos(amigo, jugadorId) !=null) ) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public void rechazarPeticion(int jugadorId, int rechazadoId) {
		 this.jugadorRepo.rechazarPeticion(jugadorId, rechazadoId);
	}
	
	

    public void deleteJugadorById(int id){
        this.jugadorRepo.deleteById(id);
    }
	
}
