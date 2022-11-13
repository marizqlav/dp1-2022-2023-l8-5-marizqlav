package org.springframework.samples.idus_martii.jugador;

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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
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
	
	public void save(Jugador j) {
		this.jugadorRepo.save(j);
		this.userService.saveUser(j.getUser());
		this.authoritiesService.saveAuthorities(j.getUser().getUsername(), "player");;
		
		
	}
	
}
