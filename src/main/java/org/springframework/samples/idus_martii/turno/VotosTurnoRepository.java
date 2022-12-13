package org.springframework.samples.idus_martii.turno;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotosTurnoRepository extends CrudRepository<VotosTurno, Integer>{

    
}
