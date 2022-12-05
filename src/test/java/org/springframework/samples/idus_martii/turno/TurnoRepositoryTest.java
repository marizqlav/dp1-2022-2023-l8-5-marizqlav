package org.springframework.samples.idus_martii.turno;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.turno.VotosTurno;

@DataJpaTest
public class TurnoRepositoryTest {
	@Autowired
	TurnoRepository turnoRepository;
	

	@Test
	public void testFindVotoByturnoAndPlayer() {
		Jugador j1=new Jugador();
		Turno t1=new Turno();
		j1.setId(1);
		t1.setId(1);		
		turnoRepository.anadirVotoTurno(t1.getId(), j1.getId(), "Traidor");
		turnoRepository.save(t1);
		VotosTurno votosTurnoPorJugador= turnoRepository.findVotoByturnoAndPlayer(1, 1);
		assertNotNull(votosTurnoPorJugador);
	}

	@Test
	public void testAnadirVotoTurno() {
		turnoRepository.anadirVotoTurno(1, 2, "Traidor");
		assertNotNull(turnoRepository.findVotoByturnoAndPlayer(1, 2));
	}	
}