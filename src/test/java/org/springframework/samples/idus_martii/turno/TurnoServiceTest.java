package org.springframework.samples.idus_martii.turno;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TurnoServiceTest {

	@Autowired
	protected TurnoService turnoService;
		
	@Autowired
	protected JugadorService jugadorService;
	
	@Autowired
	protected VotosTurnoRepository votosTurnoRepository;
		
	@Test
	void getAllTest() {
		List<Turno> turnos = this.turnoService.getTurnos();
		assertThat(turnos.size()).isEqualTo(2);
		Turno[] turnoArray = turnos.toArray(new Turno[turnos.size()]);
		assertThat(turnoArray[0].getId()).isEqualTo(1);		
	}
	
	@Test
    public void getTurnoByIdTest() throws Exception {
		Turno turno = this.turnoService.getById(2);
		assertThat(turno.getId()).isEqualTo(2);
		assertThat(turno.getConsul().getId()).isEqualTo(2);
	}
	
	
	@Test
    public void anadirVoto() throws Exception {
        List<Jugador> jugadoresValidos = new ArrayList<>();
    	Turno turno = this.turnoService.getById(1);
	}

	
	@Test
    public void cambiarVoto() throws Exception {
        List<Jugador> jugadoresValidos = new ArrayList<>();
    	Turno turno = this.turnoService.getById(1);
	}


	
	@Test
    public void findVoto() throws Exception {
		Turno turno1 = this.turnoService.getById(1);
		Jugador jugador4 = this.jugadorService.getJugadorById(4);
		VotosTurno votoTurno=this.turnoService.findVoto(turno1.getId(), jugador4.getId());
		assertThat(votoTurno.getJugador().getUser().getUsername()).isEqualTo("marolmmar1");
		assertThat(votoTurno.getTurno().getConsul().getId()).isEqualTo(2);
	}


	@Test
    public void anadirVotoTurno() throws Exception {
		Turno turno = this.turnoService.getById(1);
		Jugador jugador=this.jugadorService.getJugadorById(6);
        VotosTurno votosTurno = this.turnoService.findVoto(null, null);
        votosTurno.setJugador(jugador);
        votosTurno.setTurno(turno);
        votosTurno.setTipoVoto(FaccionesEnumerado.Traidor);
        this.votosTurnoRepository.save(votosTurno);
        votosTurno=this.votosTurnoRepository.findVotoByturnoAndPlayer(turno.getId(), jugador.getId());
		assertThat(votosTurno.getTipoVoto()).isEqualTo(FaccionesEnumerado.Traidor);

	}
	
	
	
	@Test
    public void espiarVoto() throws Exception {
        List<Jugador> jugadoresValidos = new ArrayList<>();
    	Turno turno = this.turnoService.getById(1);
	}

	
	@Test
    public void getJugadoresValidosParaRol() throws Exception {
        List<Jugador> jugadoresValidos = new ArrayList<>();
    	Turno turno = this.turnoService.getById(1);
	}

	
	@Test
    public void asignarRol() throws Exception {
        List<Jugador> jugadoresValidos = new ArrayList<>();
    	Turno turno = this.turnoService.getById(1);
	}


	
	
//    TurnoService turnoService;
//
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
