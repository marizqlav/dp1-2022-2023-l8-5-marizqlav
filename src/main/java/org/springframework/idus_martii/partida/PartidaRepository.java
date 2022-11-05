package org.springframework.idus_martii.partida;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface PartidaRepository extends CrudRepository<Partida, Integer> {
    
}
