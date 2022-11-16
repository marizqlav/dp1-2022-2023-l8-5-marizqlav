package org.springframework.samples.idus_martii.turno;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository extends CrudRepository<Turno, Integer>{
    List<Turno> findAll();
    
    @Query("SELECT v.tipoVoto FROM VotosTurno v WHERE v.turno.id=:turno_id AND v.jugador.id=:jugador_id")
    FaccionesEnumerado espiarVoto(@Param("turno_id") int turno_id,@Param("jugador_id") int jugador_id);
}
