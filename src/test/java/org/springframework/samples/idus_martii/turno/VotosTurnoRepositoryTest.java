package org.springframework.samples.idus_martii.turno;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;

@DataJpaTest
public class VotosTurnoRepositoryTest {
	@Autowired
	VotosTurnoRepository votosTurnoRepository;
		
	@Test
	public void testFindVotoByturnoAndPlayer() {
		Turno t1=new Turno();
		Jugador j1 = new Jugador();
		VotosTurno votos= new VotosTurno();
		t1.setId(1);
		j1.setId(1);
		votos.setId(1);
		votos.setJugador(j1);
		votos.setTurno(t1);
		votos.setTipoVoto(FaccionesEnumerado.Leal);
		votosTurnoRepository.save(votos);
		VotosTurno vT = votosTurnoRepository.findVotoByturnoAndPlayer(t1.getId(), j1.getId());
		assertNotNull(vT);
		assertFalse(vT.isNew());		
	}

}