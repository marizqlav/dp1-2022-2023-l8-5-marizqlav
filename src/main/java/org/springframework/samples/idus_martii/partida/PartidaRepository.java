package org.springframework.samples.idus_martii.partida;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface PartidaRepository extends CrudRepository<Partida, Integer> {
    List<Partida> findAll();
}