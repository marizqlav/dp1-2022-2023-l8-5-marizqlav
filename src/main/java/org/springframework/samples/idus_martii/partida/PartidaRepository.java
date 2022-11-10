package org.springframework.samples.idus_martii.partida;

import org.springframework.stereotype.Repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.idus_martii.jugador.Jugador;

@Repository
public interface PartidaRepository extends CrudRepository<Partida, Integer> {

    @Query("SELECT j FROM Partida p JOIN p.faccionesJugadoras f JOIN f.jugador j WHERE p.id LIKE :idPartida%")
    Set<Jugador> findJugadores(Integer idPartida);
    
}