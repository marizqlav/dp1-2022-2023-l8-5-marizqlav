package org.springframework.samples.idus_martii.turno;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TurnoRepository extends CrudRepository<Turno, Integer>{
    List<Turno> findAll();
    
    @Query("SELECT v.tipoVoto FROM VotosTurno v WHERE v.turno.id=:turno_id AND v.jugador.id=:jugador_id")
    FaccionesEnumerado espiarVoto(@Param("turno_id") int turno_id,@Param("jugador_id") int jugador_id);
    
    @Query("SELECT v FROM VotosTurno v WHERE v.turno.id=:turno_id AND v.jugador.id=:jugador_id")
    VotosTurno findVotoByturnoAndPlayer(@Param("turno_id") int turno_id,@Param("jugador_id") int jugador_id);
    
    
    @Transactional
	@Modifying
	@Query(value = "INSERT INTO votos_turno(turno_id,jugador_id) VALUES (:turnoId,:jugadorId)", nativeQuery = true)
	Integer anadirVotoTurno(@Param("turnoId") int turnoId, @Param("jugadorId") int jugadorId);
    
    @Query("SELECT t FROM Turno WHERE t.turno_partida=:i")
    Turno turnoPorNumero(@Param("i") int i);
   

}
