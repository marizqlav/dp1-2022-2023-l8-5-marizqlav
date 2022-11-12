package org.springframework.samples.idus_martii.jugador;

import java.util.List;

import org.springframework.samples.idus_martii.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends CrudRepository<Jugador, Integer>{
	
	@Query("SELECT j FROM Jugador j")
	List<Jugador> findAllJugadores();
	/*
	@Query("SELECT j FROM Jugador j WHERE j.id = :id")
	Jugador findJugadorById(int id);*/
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	User findUserByJugador(String username);
}
