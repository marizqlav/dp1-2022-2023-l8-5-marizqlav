package org.springframework.samples.idus_martii.faccion;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.stereotype.Repository;

@Repository
public interface FaccionRepository extends CrudRepository<Faccion, Integer> {
	
	List<Faccion> findAll();
	
	Faccion findById(int id);
	
	Faccion save (Faccion f);
	
	@Query("SELECT f.jugador FROM Faccion f WHERE f.partida.id LIKE :idPartida")
	List<Jugador> getJugadoresPartida(@Param("idPartida") Integer idPartida);
	
	@Query("SELECT f FROM Faccion f WHERE f.partida.id LIKE :idPartida")
	List<Faccion> getFaccionesPartida(@Param("idPartida") Integer idPartida);
	
	@Query("SELECT f FROM Faccion f WHERE f.partida.id = :idpartida AND f.jugador.id = :idjugador")
	Faccion getFaccionJugadorPartida(@Param("idjugador") Integer idjugador, @Param("idpartida") Integer idpartida);
}
