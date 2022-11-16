package org.springframework.samples.idus_martii.turno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.expression.AccessException;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TurnoServiceTest {

    TurnoService turnoService;

    @Mock
    TurnoRepository repo;

    @BeforeEach
    void setUp() {
        this.turnoService = new TurnoService(repo);
    }

    @Test
    public void testAnadirVotoPositivo() {
        Turno turno = new Turno();
        Jugador jugador = new Jugador();
        turno.setEdil1(jugador);
        turno.setVotosNeutrales(0);

        Optional<Turno> t = Optional.of(turno);
        when(repo.findById(any(Integer.class))).thenReturn(t);

        try {
            turnoService.anadirVotoVerde(0, jugador);
        } catch (Exception e) {
            assertNull(e);
        }

        turno.setVotosLeales(1);
        verify(repo, times(1)).save(turno);
    }

    @Test
    public void testAnadirVotoNegativo() {
        Turno turno = new Turno();
        Jugador jugador = new Jugador();

        Optional<Turno> t = Optional.of(turno);
        when(repo.findById(any(Integer.class))).thenReturn(t);

        assertThrows(AccessException.class, () -> turnoService.anadirVotoVerde(0, jugador));
    }
    
}
