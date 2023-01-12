package org.springframework.samples.idus_martii.jugador;

import java.util.List;


import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.idus_martii.user.User;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer>{
	List<Jugador> findAll();
	
	@Query("SELECT j FROM Jugador j WHERE j.user.username=?1")
	Jugador findByName(String name);
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO amigos(jugador_id,jugador) VALUES (:idjugador,:idamigo)", nativeQuery = true)
	Integer anadirAmigo(@Param("idjugador") int idjugador, @Param("idamigo") int idamigo);
	
	@Query("SELECT j FROM Jugador j")
	List<Jugador> findAllJugadores();
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	User findUserByJugador(@Param("username") String username);
	@Query("SELECT j FROM Jugador j, User u WHERE u.username LIKE :username% AND j.user=u.id")
	List<Jugador> findJugadorByUsername(@Param("username") String username);
	
	@Query(value ="SELECT a.jugador_id, a.jugador FROM amigos a WHERE a.jugador_id = :jugadorId AND a.jugador = :amigo", nativeQuery = true)	
	<E> E noSonAmigos(@Param("jugadorId") int jugadorId, @Param("amigo")int amigo);
	
	@Query(value ="SELECT a.jugador_id FROM amigos a WHERE a.jugador = :jugadorId", nativeQuery = true)	
	List<Integer> findPeticionesAmistadJugador(@Param("jugadorId") int jugadorId);
	
	@Query(value ="SELECT a.jugador_id FROM amigos a WHERE a.jugador = :jugadorId AND a.jugador_id = :amigoId", nativeQuery = true)	
	Integer sonAmigos(@Param("jugadorId") int jugadorId,@Param("amigoId") int amigoId);
	
	@Transactional
	@Modifying
    @Query(value ="DELETE FROM amigos a where a.jugador=:jugadorId and a.jugador_id = :rechazadoId",nativeQuery = true)
    void rechazarPeticion(@Param("jugadorId") int jugadorId,@Param("rechazadoId") int rechazadoId);

}
