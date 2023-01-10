package org.springframework.samples.idus_martii.jugador;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.idus_martii.user.User;

@DataJpaTest
public class JugadorRepositoryTest {
	@Autowired
	JugadorRepository jugadorRepository;
	
	@Test
	public void testFindByName() {
		Jugador jugadorPorNombre=jugadorRepository.findByName("Ale");
		assertNotNull(jugadorPorNombre);
		assertFalse(jugadorPorNombre.isNew());
	}
	
	@ParameterizedTest
	@CsvSource({
		"username, Ale"
	})
	public void testFindByNameParametrizada(String jugadorPorNombre) {
		assertNotNull(jugadorPorNombre);
	}

	@Test
	public void testFindAllJugadores() {
		List<Jugador> listaJugadores=jugadorRepository.findAllJugadores();
		assertNotNull(listaJugadores);
		assertFalse(listaJugadores.isEmpty());
	}
	
	@Test
	public void testFindUserByJugador() {
		User userPorJugador=jugadorRepository.findUserByJugador("ismherram");
		assertNotNull(userPorJugador);
		assertTrue(userPorJugador.isEnabled());
	}
	
	@ParameterizedTest
	@CsvSource({
		"username, ismherram"
	})
	public void testFindUserByJugadorParametrizada(String userPorJugador) {
		assertNotNull(userPorJugador);
	}
	
	@Test
	public void testFindJugadorByUsername() {
		List<Jugador> jugadorPorUsername=jugadorRepository.findJugadorByUsername("ismherram");
		assertNotNull(jugadorPorUsername);
		assertFalse(jugadorPorUsername.isEmpty());
	}
		
	@Test
	public void testNoSonAmigos() {
		Boolean noSeConocen=jugadorRepository.noSonAmigos(1, 2) !=null || jugadorRepository.noSonAmigos(2, 1) !=null;
		assertNotNull(noSeConocen);
		assertTrue(noSeConocen);	
	}
	
	@Test
	public void testAnadirAmigo() {
		Integer amigoAnadido=jugadorRepository.anadirAmigo(1, 4);
		assertNotNull(amigoAnadido);
		assertEquals(amigoAnadido, 1);
	}
	
	@Test
	public void testFindPeticionesAmistadJugador() {
		List<Integer> listaPeticionesAmistadJugador=jugadorRepository.findPeticionesAmistadJugador(1);
		assertNotNull(listaPeticionesAmistadJugador);
		assertFalse(listaPeticionesAmistadJugador.isEmpty());
	}

	@Test
	public void testSonAmigos() {
		Integer sonAmigos=jugadorRepository.sonAmigos(1, 2);
		assertNotNull(sonAmigos);
		assertEquals(sonAmigos, 2);
	}
	
	@Test
	public void testRechazarPeticion() {
		jugadorRepository.rechazarPeticion(2,3);
	}
}