package org.springframework.samples.idus_martii.partida;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.same;
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
import org.springframework.samples.idus_martii.partida.Exceptions.InitiationException;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.samples.idus_martii.turno.Estados.EstadoTurnoConverter;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
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
    @Mock
    EstadoTurnoConverter estadoTurnoConverter;

    @BeforeEach
    void setUp() {
        this.partidaService = new PartidaService(partidaRepo, turnoService, rondaService, faccionService, estadoTurnoConverter);
    }

    /*@Test
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
            partidaService.iniciarPartida(0, lobby);
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

        assertThrows(InitiationException.class, () -> partidaService.iniciarPartida(0, null));
    }
    
    @Test
    public void testTurnoActual() {
    	
    	 Partida partida = new Partida();
         partida.setNumeroJugadores(5);

         Optional<Partida> p = Optional.of(partida);
         when(partidaRepo.findById(any(Integer.class))).thenReturn(p);
         
         
         Ronda r = new Ronda(); 
     	partida.setRondas(Arrays.asList(r));
     	Turno t = new Turno();
     	partida.getRondas().get(0).setTurnos(Arrays.asList(t));
     	

     	assertEquals(t, partidaService.getTurnoActual(0));
    }

    @Test
    public void testRoles() {

        Partida partida = new Partida();
        Ronda ronda = new Ronda();
        Turno turno = new Turno();
        partida.setRondas(Arrays.asList(ronda));
        ronda.setTurnos(Arrays.asList(turno));

        Jugador j1 = new Jugador();
        Jugador j2 = new Jugador();
        Jugador j3 = new Jugador();
        Jugador j4 = new Jugador();
        Jugador j5 = new Jugador();

        turno.setConsul(j2);
        turno.setPredor(j3);
        turno.setEdil1(j4);
        turno.setEdil2(j5);

        when(partidaRepo.findById(any(Integer.class))).thenReturn(Optional.of(partida));  
        when(partidaRepo.findJugadores(any(Integer.class))).thenReturn(Arrays.asList(j1, j2, j3, j4, j5));

        partidaService.rotarRoles(0);       

        ArgumentCaptor<Turno> agTurno = ArgumentCaptor.forClass(Turno.class);
        verify(turnoService, times(1)).save(agTurno.capture());
        assertEquals(agTurno.getValue().getConsul(), j3);
        assertEquals(agTurno.getValue().getPredor(), j4);
        assertEquals(agTurno.getValue().getEdil1(), j5);
        assertEquals(agTurno.getValue().getEdil2(), j1);
    }*/


	@ParameterizedTest
	@CsvSource({
		"14, 1, Traidor",
		"3, 1, null",
		"10, 9, Mercader",
        "11, 8, Leal"
	})
    public void testFaccionGanadora(Integer votosLeales, Integer votosTraidores, String expected) {

        Partida partida = new Partida();
        partida.setNumeroJugadores(5);
        Ronda ronda1 = new Ronda();
        ronda1.setId(88);
        Ronda ronda2 = new Ronda();
        ronda2.setId(89);
        Integer n1 = votosLeales;
        Integer n2 = votosTraidores;
        partida.getRondas().add(ronda1);
        ronda1.setPartida(partida);
        for (int i = 0; i < 5; i++) {
            Turno turno = new Turno();
            if (n1 > 0) {
                turno.setVotosLeales(Math.min(n1, 2));
                n1 -= 2;
            } else
            if (n2 > 0) {
                turno.setVotosTraidores(Math.min(n2, 2));
                n2 -= 2;
            } else {
                continue;
            }
            turno.setId(i);
            turno.setRonda(ronda1);
            ronda1.getTurnos().add(turno);
        }

        if (n1 > 0 || n2 > 0) {
            partida.getRondas().add(ronda2);
            ronda2.setPartida(partida);
            for (int i = 0; i < 5; i++) {
                Turno turno = new Turno();
                if (n1 > 0) {
                    turno.setVotosLeales(Math.min(n1, 2));
                    n1 -= 2;
                } else
                if (n2 > 0) {
                    turno.setVotosTraidores(Math.min(n2, 2));
                    n2 -= 2;
                } else {
                    continue;
                }
                turno.setId(i + 10);
                turno.setRonda(ronda2);
                ronda2.getTurnos().add(turno);
            }
        }

        when(partidaRepo.findById(0)).thenReturn(Optional.of(partida));

        FaccionesEnumerado f = partidaService.getFaccionGanadora(0);

        if (expected.equals("null")) {
            assertNull(f);
        } else {
            assertNotNull(f);
            assertEquals(f.toString(), expected);
        }
    }

    @Test
    public void testGetJugadoresFromFaccionEnum() {

        Partida partida = new Partida();
        Faccion f1 = new Faccion();
        Jugador j1 = new Jugador();
        f1.setJugador(j1);
        f1.setFaccionSelecionada(FaccionesEnumerado.Leal);
        partida.getFaccionesJugadoras().add(f1);
        Faccion f2 = new Faccion();
        Jugador j2 = new Jugador();
        f2.setJugador(j2);
        f2.setFaccionSelecionada(FaccionesEnumerado.Traidor);
        partida.getFaccionesJugadoras().add(f2);
        Faccion f3 = new Faccion();
        Jugador j3 = new Jugador();
        f3.setJugador(j3);
        f3.setFaccionSelecionada(FaccionesEnumerado.Mercader);
        partida.getFaccionesJugadoras().add(f3);

        when(partidaRepo.findById(0)).thenReturn(Optional.of(partida));

        List<Jugador> res = partidaService.getJugadoresFromFaccionEnum(0, FaccionesEnumerado.Leal);

        assertEquals(res.get(0), j1);
    }

}
