package org.springframework.samples.idus_martii.partida;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.jugador.Jugador;

@DataJpaTest
public class PartidaRepositoryTest {
	
	@Autowired
	PartidaRepository partidaRepository;
		
	@Test
	public void testFindAllEnJuego() {
		List<Partida> partidosEnJuego = partidaRepository.findAllEnJuego();
		assertNotNull(partidosEnJuego);
		assertFalse(partidosEnJuego.isEmpty());		
	}
	
	@Test
	public void testFindJugadores() {
		List<Jugador> listaJugadores= partidaRepository.findJugadores(1);
		assertNotNull(listaJugadores);
		assertFalse(listaJugadores.isEmpty());
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

	@Test
	public void testFindAllFinalizadasJugador() {
		List<Partida> listaMatchFinalizadas=partidaRepository.findAllFinalizadasJugador(1);
		assertNotNull(listaMatchFinalizadas);
		assertFalse(listaMatchFinalizadas.isEmpty());
	}
	
	@Test
	public void testFindAllFinalizadas() {
		List<Partida> partidosEnJuego = partidaRepository.findAllFinalizadas();
		assertNotNull(partidosEnJuego);
		assertFalse(partidosEnJuego.isEmpty());		
	}

	@Test
	public void testFindAllCreadasJugador() {
		List<Partida> partidosEnJuego = partidaRepository.findAllCreadasJugador(1);
		assertNotNull(partidosEnJuego);
		assertFalse(partidosEnJuego.isEmpty());		
	}
	
	@Test
	public void testFindFaccionesPartida() {
		List<Faccion> listaFacciones= partidaRepository.findFaccionesPartida(1);
		assertNotNull(listaFacciones);
		assertFalse(listaFacciones.isEmpty());
	}
	
	public void testEliminarLobby() {
		partidaRepository.eliminarLobby(1);
		assertNull(partidaRepository.getLobby(1).getId());
	}
	
	@Test
	public void testGetLobby() {
        partidaRepository.createLobby(1, 1);
		Lobby lobbyPartida=partidaRepository.getLobby(1);
		assertNotNull(lobbyPartida);
		assertFalse(lobbyPartida.isNew());
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
	
	@Test
	public void testFindJugadorInLobby() {
		partidaRepository.createLobby(1, 1);
		partidaRepository.addJugadorLobby(1, 1);
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
	
}