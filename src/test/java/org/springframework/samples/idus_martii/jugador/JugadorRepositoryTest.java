package org.springframework.samples.idus_martii.jugador;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
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
	
	@Test
	public void testFindJugadorByUsername() {
		List<Jugador> jugadorPorUsername=jugadorRepository.findJugadorByUsername("ismherram");
		assertNotNull(jugadorPorUsername);
		assertFalse(jugadorPorUsername.isEmpty());
	}
	
	//No tengo ni idea
//	@Query(value ="SELECT a.jugador_id, a.jugador FROM amigos a WHERE a.jugador_id = :jugadorId AND a.jugador = :amigo", nativeQuery = true)	
//	<E> E noSonAmigos(@Param("jugadorId") int jugadorId, @Param("amigo")int amigo);
	
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
}