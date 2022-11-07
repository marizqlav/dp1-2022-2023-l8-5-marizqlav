package org.springframework.samples.idus_martii.mensaje;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends CrudRepository<Mensaje, Integer>{
    List<Mensaje> findAll();
}
