package org.springframework.samples.idus_martii.faccion;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaccionRepository extends CrudRepository<Faccion, Integer> {
	
	@Query("SELECT f FROM Faccion f")
	List<Faccion> findAll();
	
	Faccion findById(int id);
	
	Faccion save (Faccion f);
	
	
	
	
	

}
