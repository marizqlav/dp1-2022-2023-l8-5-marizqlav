package org.springframework.samples.idus_martii.partida;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;

@Repository
public interface PartidaRepository extends CrudRepository<Partida, Integer> {

    List<Partida> findAll();
    
    Optional<Partida> findById(int id);

    @Query("SELECT p FROM Partida p WHERE p.fechaFin = null")
	List<Partida> findAllEnJuego();
    
    Optional<Partida> findById(Integer id);

    @Query("SELECT j FROM Partida p JOIN p.faccionesJugadoras f JOIN f.jugador j WHERE p.id LIKE :idPartida")
    List<Jugador> findJugadores(@Param("idPartida") Integer idPartida);
    
    @Query("SELECT p FROM Partida p WHERE p.jugador.id = :idjugador AND p.fechaFin IS NULL ")
    Partida jugadorPartidaEnCurso(@Param("idjugador") Integer idjugador);
    
    @Query("SELECT p FROM Partida p WHERE p.id = :idpartida AND p.fechaInicio IS NOT NULL ")
    Partida findPartidaIniciada(@Param("idpartida") Integer idpartida);
    
    @Query("SELECT p FROM Partida p, Faccion f, Jugador j WHERE p.id = f.partida AND f.jugador = j.id AND j.id = :idjugador AND p.fechaFin IS NOT NULL AND p.faccionGanadora IS NOT NULL")
    List<Partida> findAllFinalizadasJugador(@Param("idjugador") int idjugador);
    
    @Modifying
    @Query("DELETE FROM Lobby l where l.partida=:partidaId")
    void eliminarLobby(@Param("partidaId") int partidaId);
    
    @Query("SELECT l FROM Lobby l WHERE l.partida.id = :idpartida")
	Lobby getLobby(@Param("idpartida") int idpartida);
    
    @Transactional
	@Modifying
	@Query(value = "INSERT INTO lobby_jugadores(lobby_id,jugadores_id) VALUES (:idlobby,:idjugador)", nativeQuery = true)
	Integer anadirJugadorLobby(@Param("idjugador") int idjugador, @Param("idlobby") int idlobby);

    @Transactional  
	@Modifying
	@Query(value = "INSERT INTO lobby(id, partida_id) VALUES (:idlobby, :idpartida)", nativeQuery = true)
	Integer anadirLobby(@Param("idlobby") int idlobby, @Param("idpartida") int idpartida);
    

    @Query("SELECT j FROM Lobby l JOIN l.jugadores j WHERE j.id = :idjugador AND l.id = :idlobby")
	Jugador findJugadorInLobby(@Param("idjugador") Integer idjugador,@Param("idlobby") Integer idlobby);
    
    @Query("SELECT t.votosLeales FROM Turno t WHERE t.ronda.partida.id = :idpartida")
	List<Integer> getVotosFavor(@Param("idpartida") Integer idpartida);
    
    @Query("SELECT t.votosTraidores FROM Turno t WHERE t.ronda.partida.id = :idpartida")
	List<Integer> getVotosContra(@Param("idpartida") Integer idpartida);
    
    
    @Query("SELECT p FROM Partida p, Faccion f, Jugador j WHERE p.id = f.partida AND f.jugador = j.id "
    		+ "AND j.id = :idjugador AND p.fechaFin IS NOT NULL AND p.faccionGanadora = f.faccionSelecionada ")
    List<Partida> findPartidasGanadas(@Param("idjugador") int idjugador);
}