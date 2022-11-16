package org.springframework.samples.idus_martii.mensaje;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends CrudRepository<Mensaje, Integer>{
	@Query("SELECT m FROM Mensaje m")
    List<Mensaje> findAll();
    Mensaje save(Mensaje mensaje);
    Optional<Mensaje> findById(int id);
    @Query("SELECT m FROM Mensaje m WHERE m.partida.id = :idpartida")
    List<Mensaje> findMensajesByPartidaId(@Param("idpartida") Integer idpartida);
}
