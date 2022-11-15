package org.springframework.samples.idus_martii.ronda;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RondaRepository extends CrudRepository<Ronda, Integer>{
    List<Ronda> findAll();
    
    @Transactional  
	@Modifying
	@Query(value = "INSERT INTO ronda(num_ronda,partida_id) VALUES (:num_ronda,:idpartida)", nativeQuery = true)
	Integer anadirRonda(@Param("num_ronda") int num_ronda, @Param("idpartida") int idpartida);
}
