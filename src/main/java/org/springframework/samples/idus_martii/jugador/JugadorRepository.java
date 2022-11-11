package org.springframework.samples.idus_martii.jugador;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
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
	
}
