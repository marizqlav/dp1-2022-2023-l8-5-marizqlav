package org.springframework.idus_martii.partida;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RondaRepository extends CrudRepository<Ronda, Integer> {
    
}
