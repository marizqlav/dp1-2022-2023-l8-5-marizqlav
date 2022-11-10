package org.springframework.samples.idus_martii.jugador;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends CrudRepository<Jugador, Integer>{
	
	@Query("SELECT j FROM Jugador j")
	List<Jugador> findAllJugadores();
}
