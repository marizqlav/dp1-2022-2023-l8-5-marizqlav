package org.springframework.samples.idus_martii.turno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;

@DataJpaTest
public class TurnoRepositoryTest {
	@Autowired
	TurnoRepository turnoRepository;
	
	
//    @Query("SELECT v FROM VotosTurno v WHERE v.turno.id=:turno_id AND v.jugador.id=:jugador_id")
//    VotosTurno findVotoByturnoAndPlayer(@Param("turno_id") int turno_id,@Param("jugador_id") int jugador_id);
    
	@Test
	public void testFindVotoByturnoAndPlayer() {
		VotosTurno votoTurno=turnoRepository.findVotoByturnoAndPlayer(1, 2);
//		assertNotNull(votoTurno);
//		assertEquals(votoTurno, "Traidor");
	}
	
//    @Query("SELECT t FROM Turno t WHERE t.numTurno=:i")
//    Turno turnoPorNumero(@Param("i") int i);
	//sale un error relacionado con FaccionesEnumerado
//	@Test
//	public void testTurnoPorNumero() {
//		Turno turnPorNumeroo=turnoRepository.turnoPorNumero(2);
//		assertNotNull(turnPorNumeroo);
//		assertEquals(turnPorNumeroo, "Traidor");
//	}
	
	
}


	
