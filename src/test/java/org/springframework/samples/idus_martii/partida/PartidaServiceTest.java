package org.springframework.samples.idus_martii.partida;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PartidaServiceTest {
    
    PartidaService partidaService;

    @Mock
    PartidaRepository partidaRepo;
    @Mock
    JugadorService jugadorService;
    @Mock
    TurnoService turnoService;
    @Mock
    RondaService rondaService;
    @Mock
    FaccionService faccionService;

    @BeforeEach
    void setUp() {
        this.partidaService = new PartidaService(partidaRepo, turnoService, rondaService, faccionService);
    }

    @Test
    public void testIniciarPartidaPositivo() {
        Partida partida = new Partida();
        partida.setNumeroJugadores(5);

        Optional<Partida> p = Optional.of(partida);
        when(partidaRepo.findById(any(Integer.class))).thenReturn(p);
        
        Lobby lobby = new Lobby();
        Jugador j1 = new Jugador();
        Jugador j2 = new Jugador();
        Jugador j3 = new Jugador();
        Jugador j4 = new Jugador();
        Jugador j5 = new Jugador();
        lobby.getJugadores().addAll(Arrays.asList(j1, j2, j3, j4, j5));

        try {
            partidaService.IniciarPartida(0, lobby);
        } catch (Exception e) {
            assert(e == null);
        }

        ArgumentCaptor<Ronda> agRonda = ArgumentCaptor.forClass(Ronda.class);
        verify(rondaService, times(1)).save(agRonda.capture());
        assertEquals(agRonda.getValue().getTurnos().size(), 1);

        ArgumentCaptor<Turno> agTurno = ArgumentCaptor.forClass(Turno.class);
        verify(turnoService, times(1)).save(agTurno.capture());
        assertTrue(lobby.getJugadores().contains(agTurno.getValue().getConsul()));
        assertTrue(lobby.getJugadores().contains(agTurno.getValue().getPredor()));
        assertTrue(lobby.getJugadores().contains(agTurno.getValue().getEdil1()));
        assertTrue(lobby.getJugadores().contains(agTurno.getValue().getEdil2()));

        ArgumentCaptor<Faccion> agFaccion = ArgumentCaptor.forClass(Faccion.class);
        verify(faccionService, atLeast(1)).save(agFaccion.capture());
        Integer leal = 0;
        Integer traidor = 0;
        Integer mercader = 0;
        for (Faccion faccion : agFaccion.getAllValues()) {
            if (faccion.getFaccionPosible1() == FaccionesEnumerado.Leal) leal++;
            if (faccion.getFaccionPosible1() == FaccionesEnumerado.Traidor) traidor++;
            if (faccion.getFaccionPosible1() == FaccionesEnumerado.Mercader) mercader++;

            if (faccion.getFaccionPosible2() == FaccionesEnumerado.Leal) leal++;
            if (faccion.getFaccionPosible2() == FaccionesEnumerado.Traidor) traidor++;
            if (faccion.getFaccionPosible2() == FaccionesEnumerado.Mercader) mercader++;
        }
        assertEquals(mercader, 2);
        assertEquals(leal, 4);
        assertEquals(traidor, 4);
    }

    @Test
    public void testIniciarPartidaNegativo() {
        Partida partida = new Partida();
        partida.setFechaInicio(LocalDateTime.now());

        Optional<Partida> p = Optional.of(partida);
        when(partidaRepo.findById(any(Integer.class))).thenReturn(p);

        assertThrows(InitiationException.class, () -> partidaService.IniciarPartida(0, null));
    }
}
