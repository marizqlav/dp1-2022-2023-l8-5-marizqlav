package org.springframework.samples.idus_martii.partida;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.VotosTurno;
import org.springframework.transaction.annotation.Transactional;

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
	
//    @Query("SELECT p FROM Partida p WHERE p.jugador.id = :idjugador AND p.fechaFin IS NULL ")
//    Partida jugadorPartidaEnCurso(@Param("idjugador") Integer idjugador);
	
	//Da error y no se que le falta
//	@Test
//	public void testJugadorPartidaEnCurso() {
//   		Partida partida = new Partida();
//   		Lobby lobby=new Lobby();
//   		lobby.setId(4);
//        Jugador j1 = new Jugador();
//        Jugador j2 = new Jugador();
//        Jugador j3 = new Jugador();
//        Jugador j4 = new Jugador();
//        Jugador j5 = new Jugador();
//        j1.setId(1);
//        j2.setId(2);
//        j3.setId(3);
//        j4.setId(4);
//        j5.setId(5);
//        partida.setNumeroJugadores(5);
//        partida.setFechaCreacion(LocalDateTime.now());
//    	partida.setLobby(lobby);
//        lobby.setPartida(partida);
//        lobby.getJugadores().addAll(Arrays.asList(j1, j2, j3, j4, j5));
//        Ronda rondaInicial = new Ronda();
//        rondaInicial.setPartida(partida);
//        Turno turnoInicial = new Turno();
//        turnoInicial.setRonda(rondaInicial);
//        partida.setLobby(lobby);
//        partidaRepository.addJugadorLobby(j1.getId(), lobby.getId());
//        partidaRepository.addJugadorLobby(j2.getId(), lobby.getId());
//        partidaRepository.addJugadorLobby(j3.getId(), lobby.getId());
//        partidaRepository.addJugadorLobby(j4.getId(), lobby.getId());
//        partidaRepository.addJugadorLobby(j5.getId(), lobby.getId());
//        System.out.println("11111111111111");
//        partidaRepository.save(partida);
//        System.out.println("ssssssssssssssssss");
//   		Partida match = partidaRepository.jugadorPartidaEnCurso(5);
//		assertNotNull(match);
//	}


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
		Jugador jugador = new Jugador();
		jugador.setId(222);
		partida.setFechaFin(null);
		partida.setJugador(jugador);
		System.out.println("jugador:" + jugador.getId());
		System.out.println("partida jugador: " + partida.getJugador().getId());
		partidaRepository.save(partida);
		System.out.println("encontrada: " + partidaRepository.jugadorPartidaEnCurso(jugador.getId()));
		
		assertEquals(partida, partidaRepository.jugadorPartidaEnCurso(jugador.getId()));

		
	}
	
}


	
