package org.springframework.samples.idus_martii.turno;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotosTurnoRepository extends CrudRepository<VotosTurno, Integer>{

    @Query("SELECT v FROM VotosTurno v WHERE v.turno.id=:turno_id AND v.jugador.id=:jugador_id")
    VotosTurno findVotoByturnoAndPlayer(@Param("turno_id") Integer turno_id, @Param("jugador_id") Integer jugador_id);

}
