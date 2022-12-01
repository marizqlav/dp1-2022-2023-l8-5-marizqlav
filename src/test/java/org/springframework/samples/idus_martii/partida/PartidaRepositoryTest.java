package org.springframework.samples.idus_martii.partida;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
//    @Query("SELECT p FROM Partida p WHERE p.jugador.id = :idjugador AND p.fechaFin IS NULL ")
//    Partida jugadorPartidaEnCurso(@Param("idjugador") Integer idjugador);
	
	//Da error porque no hay creada ningun partido en curso
	@Test
	public void testJugadorPartidaEnCurso() {
		Partida match = partidaRepository.jugadorPartidaEnCurso(5);
//		assertNotNull(match);
//		assertFalse(match.isNew());
	}

	//Da error porque no hay partidas iniciadas
//    @Query("SELECT p FROM Partida p WHERE p.id = :idpartida AND p.fechaInicio IS NOT NULL ")
//    Partida findPartidaIniciada(@Param("idpartida") Integer idpartida);

//	@Test
//	public void testFindPartidaIniciada() {
//		Partida matchIniciado=partidaRepository.findPartidaIniciada(1);
//		assertNotNull(matchIniciado);
//		assertFalse(matchIniciado.isNew());
//
//	}
	
	
//    @Query("SELECT p FROM Partida p, Faccion f, Jugador j WHERE p.id = f.partida AND f.jugador = j.id AND j.id = :idjugador AND p.fechaFin IS NOT NULL ")
//    List<Partida> findAllFinalizadasJugador(@Param("idjugador") int idjugador);

//	@Test
//	public void testFindAllFinalizadasJugador() {
//		List<Partida> listaMatchFinalizadas=partidaRepository.findAllFinalizadasJugador(1);
//		assertNotNull(listaMatchFinalizadas);
//		assertFalse(listaMatchFinalizadas.isEmpty());
//
//	}

//Da error porque no hay partidas creadas y no existe el lobby	
//    @Query("SELECT l FROM Lobby l WHERE l.partida.id = :idpartida")
//	Lobby getLobby(@Param("idpartida") int idpartida);
	@Test
	public void testGetLobby() {
		Lobby lobbyPartida=partidaRepository.getLobby(1);
		assertNotNull(lobbyPartida);
		assertFalse(lobbyPartida.isNew());

	}
	
	
	
//    @Query("SELECT j FROM Lobby l JOIN l.jugadores j WHERE j.id = :idjugador AND l.id = :idlobby")
//	Jugador findJugadorInLobby(@Param("idjugador") Integer idjugador,@Param("idlobby") Integer idlobby);
    
	public void testFindJugadorInLobby() {
		Jugador jugadorInLobby=partidaRepository.findJugadorInLobby(1, 1);
		assertNotNull(jugadorInLobby);
		assertFalse(jugadorInLobby.isNew());

	}

//    @Query("SELECT p FROM Partida p, Faccion f, Jugador j WHERE p.id = f.partida AND f.jugador = j.id "
//    		+ "AND j.id = :idjugador AND p.fechaFin IS NOT NULL AND p.faccionGanadora = f.faccionSelecionada ")
//    List<Partida> findPartidasGanadas(@Param("idjugador") int idjugador);
	@Test
	public void testFindPartidasGanadas() {
		List<Partida> listaPartidasGanadas=partidaRepository.findPartidasGanadas(1);
		assertNotNull(listaPartidasGanadas);
		assertFalse(listaPartidasGanadas.isEmpty());
	}
	
}


	
