package org.springframework.samples.idus_martii.ronda;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RondaRepository extends CrudRepository<Ronda, Integer>{
    List<Ronda> findAll();
}
