package org.springframework.samples.idus_martii.partida;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.idus_martii.jugador.Jugador;

@DataJpaTest
public class PartidaRepositoryTest {
	@Autowired
	PartidaRepository partidaRepository;
		
	@Test
	public void testFindAllEnJuego() {
		List<Partida> partidosEnJuego = partidaRepository.findAllEnJuego();
		assertNotNull(partidosEnJuego);
		assertTrue(partidosEnJuego.isEmpty());		
	}
	
	@Test
	public void testFindJugadores() {
		List<Jugador> listaJugadores= partidaRepository.findJugadores(1);
		assertNotNull(listaJugadores);
		assertFalse(listaJugadores.isEmpty());
		List<Jugador> listaJugadores2 = partidaRepository.findJugadores(2);
		assertNotNull(listaJugadores2);
		assertTrue(listaJugadores2.isEmpty());
	}


	@Test
	public void testFindAllFinalizadasJugador() {
		List<Partida> listaMatchFinalizadas=partidaRepository.findAllFinalizadasJugador(1);
		assertNotNull(listaMatchFinalizadas);
		assertFalse(listaMatchFinalizadas.isEmpty());

	}

	@Test
	public void testGetLobby() {
        Partida partida = new Partida();
        Lobby lobby = new Lobby();
        lobby.setId(2);
        partida.setNumeroJugadores(5);
        partida.setFechaCreacion(LocalDateTime.now());
    	partida.setLobby(lobby);
        lobby.setPartida(partida);
    	Jugador jug1=new Jugador();
    	partida.setJugador(jug1);
        partidaRepository.save(partida);
        partidaRepository.createLobby(lobby.getId(), partida.getId());
		Lobby lobbyPartida=partidaRepository.getLobby(2);
		assertNotNull(lobbyPartida);
		assertFalse(lobbyPartida.isNew());
	}
	
	public void testEliminarLobby() {
		partidaRepository.eliminarLobby(1);
		assertNull(partidaRepository.getLobby(1).getId());
	}

	@Test
	public void testCreateLobby() {

		partidaRepository.createLobby(1, 1);
		assertNotNull(partidaRepository.getLobby(1).getId());
	}	

	@Test
	public void testAddJugadorLobby() {
        Partida partida = new Partida();
        Lobby lobby = new Lobby();
        lobby.setId(2);
        partida.setNumeroJugadores(5);
        partida.setFechaCreacion(LocalDateTime.now());
    	partida.setLobby(lobby);
        lobby.setPartida(partida);
    	Jugador jug1=new Jugador();
    	partida.setJugador(jug1);
        partidaRepository.save(partida);
        partidaRepository.createLobby(lobby.getId(), partida.getId());
		partidaRepository.addJugadorLobby(jug1.getId(), 2);
		assertNotNull(partidaRepository.findJugadorInLobby(jug1.getId(), 2));
	}	
	    
	public void testFindJugadorInLobby() {
		Jugador jugadorInLobby=partidaRepository.findJugadorInLobby(1, 1);
		assertNotNull(jugadorInLobby);
		assertFalse(jugadorInLobby.isNew());
	}

	@Test
	public void testFindPartidasGanadas() {
		List<Partida> listaPartidasGanadas=partidaRepository.findPartidasGanadas(1);
		assertNotNull(listaPartidasGanadas);
		assertFalse(listaPartidasGanadas.isEmpty());
	}
	
	@Test
	public void testJugadorPartidaEnCurso() {
        Partida partida = new Partida();
        Lobby lobby = new Lobby();
        lobby.setId(2);
        partida.setNumeroJugadores(5);
        partida.setFechaCreacion(LocalDateTime.now());
    	partida.setLobby(lobby);
        lobby.setPartida(partida);
    	Jugador jug1=new Jugador();
    	partida.setJugador(jug1);
		partidaRepository.save(partida);
		assertEquals(partida, partidaRepository.jugadorPartidaEnCurso(jug1.getId()));
	}	
}