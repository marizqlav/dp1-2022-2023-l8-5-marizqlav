package org.springframework.samples.idus_martii.jugador;

import java.util.List;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.idus_martii.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends CrudRepository<Jugador, Integer>{
	List<Jugador> findAll();
	
	@Query("SELECT j FROM Jugador j WHERE j.user.username=?1")
	Jugador findByName(String name);
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO amigos(jugador_id,jugador) VALUES (:idjugador,:idamigo)", nativeQuery = true)
	Integer anadirAmigo(@Param("idjugador") int idjugador, @Param("idamigo") int idamigo);
	

	@Query("SELECT j FROM Jugador j")
	List<Jugador> findAllJugadores();
	/*
	@Query("SELECT j FROM Jugador j WHERE j.id = :id")
	Jugador findJugadorById(int id);*/
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	User findUserByJugador(@Param("username") String username);
	@Query("SELECT j FROM Jugador j, User u WHERE u.username LIKE :username% AND j.user=u.id")
	List<Jugador> findJugadorByUsername(@Param("username") String username);
	
	
	

}
