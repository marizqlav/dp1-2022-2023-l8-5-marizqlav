package org.springframework.samples.idus_martii.turno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
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
    public void anadirVotoVerdeTest() {
        Turno turno = new Turno();
        Jugador jugador = new Jugador();
        turno.setEdil1(jugador);

        Optional<Turno> t = Optional.of(turno);
        when(repo.findById(any(Integer.class))).thenReturn(t);

        try {
            turnoService.anadirVotoVerde(0, jugador);
        } catch (Exception e) {
            assertNull(e);
        }
    }
    
}
