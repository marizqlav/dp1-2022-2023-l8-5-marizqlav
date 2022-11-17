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
	@Query(value = "INSERT INTO votos_turno(turno_id,jugador_id,tipo_Voto) VALUES (:turnoId,:jugadorId,:voto)", nativeQuery = true)
	void anadirVotoTurno(@Param("turnoId") int turnoId, @Param("jugadorId") int jugadorId,@Param("voto") String voto);
    
    @Query("SELECT t FROM Turno t WHERE t.numTurno=:i")
    Turno turnoPorNumero(@Param("i") int i);
    
    
    @Transactional
	@Modifying
	@Query(value = "UPDATE votos_turno SET espiado = 'Si' WHERE turno_id = :turnoid AND jugador_id= :jugadorid", nativeQuery = true)
	void espiarVotoJugador(@Param("jugadorid") int jugadorid, @Param("turnoid") int turnoid);
   

}
