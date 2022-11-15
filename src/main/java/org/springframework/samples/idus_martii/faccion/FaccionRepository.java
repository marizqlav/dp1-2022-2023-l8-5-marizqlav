package org.springframework.samples.idus_martii.faccion;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FaccionRepository extends CrudRepository<Faccion, Integer> {
	
	@Query("SELECT f FROM Faccion f")
	List<Faccion> findAll();
	
	Faccion findById(int id);
	
	Faccion save (Faccion f);
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE faccion SET faccion_selecionada = :faccionSelecionada WHERE partida_id = :partidaid AND jugador_id= :jugadorid", nativeQuery = true)
	void setFaccionSelecionada(@Param("jugadorid") int jugadorid, @Param("partidaid") int partidaid, @Param("faccionSelecionada") String faccionSelecionada);
}
