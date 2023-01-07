package org.springframework.samples.idus_martii.turno;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class TurnoServiceTest {

	@Autowired
	private TurnoService turnoService;
		
	@Autowired
	private JugadorService jugadorService;
	
	@Autowired
	private PartidaService partidaService;

		
	@Test
	void getAllTest() {
		List<Turno> turnos = this.turnoService.getTurnos();
		assertThat(turnos.size()).isEqualTo(2);
	}
	
	@Test
    void getTurnoByIdTest() throws Exception {
		Turno turno = this.turnoService.getById(2);
		assertThat(turno.getId()).isEqualTo(2);
		assertThat(turno.getConsul().getId()).isEqualTo(2);
	}
	
	
	@Test
    void anadirVoto() throws Exception {
		Turno turno = this.turnoService.getById(2);
		Jugador jugador=this.jugadorService.getJugadorById(4);
		String voto="Traidor";
		this.turnoService.anadirVoto(turno.getId(), jugador, voto);
		assertThat(turno.getVotosTraidores()).isEqualTo(1);

	}

	
	@Test
    public void cambiarVoto() throws Exception {
		Turno turno = this.turnoService.getById(1);
		Jugador jugadorPretor=this.jugadorService.getJugadorById(3);
		Jugador jugadorEdil1=this.jugadorService.getJugadorById(4);
		String voto="Traidor";
		this.turnoService.cambiarVoto(turno.getId(), jugadorPretor, jugadorEdil1.getId(), voto);
		assertThat(turno.getVotosTraidores()).isEqualTo(2);
	}


	
	@Test
    void findVoto() throws Exception {
		Turno turno1 = this.turnoService.getById(1);
		Jugador jugador4 = this.jugadorService.getJugadorById(4);
		VotosTurno votoTurno=this.turnoService.findVoto(turno1.getId(), jugador4.getId());
		assertThat(votoTurno.getJugador().getUser().getUsername()).isEqualTo("marolmmar1");
		assertThat(votoTurno.getTurno().getConsul().getId()).isEqualTo(2);
	}


	@Test
    void anadirVotoTurno() throws Exception {
		Turno turno = this.turnoService.getById(1);
		Jugador jugador=this.jugadorService.getJugadorById(4);
		this.turnoService.anadirVotoTurno(turno, jugador, FaccionesEnumerado.Traidor);
		assertThat(turno.getVotosTraidores()).isEqualTo(2);
	}
	
	
	
	@Test
    void espiarVoto() throws Exception {
		Turno turno = this.turnoService.getById(1);
		Partida partida = this.partidaService.findPartida(1);
		Jugador jugadorPretor=this.jugadorService.getJugadorById(3);
		String voto="Traidor";
		this.turnoService.espiarVoto(partida.getId(), jugadorPretor, voto);
		assertThat(turno.getPredor()).isEqualTo(jugadorPretor);
	}

	
	@Test
    void getJugadoresValidosParaRol() throws Exception {
		Turno turno = this.turnoService.getById(1);
		Partida partida = this.partidaService.findPartida(1);
		Jugador jugadorPretor=this.jugadorService.getJugadorById(3);
		String voto="Traidor";
		this.turnoService.espiarVoto(partida.getId(), jugadorPretor, voto);
		assertThat(turno.getPredor()).isEqualTo(jugadorPretor);
	}
	
	@Test
    void asignarRol() throws Exception {
		Turno turno = this.turnoService.getById(1);
		Jugador jugadorConsul=this.jugadorService.getJugadorById(3);
		this.turnoService.asignarRol(turno.getId(), jugadorConsul);
		assertThat(turno.getConsul()).isEqualTo(jugadorConsul);
	}

	
//    @Mock
//    TurnoRepository repo;
//
//    @BeforeEach
//    void setUp() {
//        this.turnoService = new TurnoService(repo);
//    }
//
//    @Test
//    public void testAnadirVotoPositivo() {
//        Turno turno = new Turno();
//        Jugador jugador = new Jugador();
//        turno.setEdil1(jugador);
//        turno.setVotosNeutrales(0);
//
//        Optional<Turno> t = Optional.of(turno);
//        when(repo.findById(any(Integer.class))).thenReturn(t);
//
//        try {
//            turnoService.anadirVotoVerde(0, jugador);
//        } catch (Exception e) {
//            assertNull(e);
//        }
//
//        turno.setVotosLeales(1);
//        verify(repo, times(1)).save(turno);
//    }
//
//    @Test
//    public void testAnadirVotoNegativo() {
//        Turno turno = new Turno();
//        Jugador jugador = new Jugador();
//
//        Optional<Turno> t = Optional.of(turno);
//        when(repo.findById(any(Integer.class))).thenReturn(t);
//
//        assertThrows(AccessException.class, () -> turnoService.anadirVotoVerde(0, jugador));
//    }
    
}
