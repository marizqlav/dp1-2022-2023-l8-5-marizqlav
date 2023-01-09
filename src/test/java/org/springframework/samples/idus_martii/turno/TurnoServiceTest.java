package org.springframework.samples.idus_martii.turno;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.expression.AccessException;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class TurnoServiceTest {

	@Autowired
	private TurnoService turnoService;
		
	@Autowired
	private JugadorService jugadorService;
	
	
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
		String voto=FaccionesEnumerado.Leal.toString();
		this.turnoService.anadirVoto(turno.getId(), jugador, voto);
		List<Turno> turnos=turnoService.getTurnos();
		assertThat(turnos.get(1).getVotosLeales()==1);
	}

	//Como en la primera ronda no esta el voto amarillo no se puede realizar ni por tanto contabilizar el voto
	@Test
    void anadirVotoFail() throws Exception {
		Turno turno = this.turnoService.getById(2);
		Jugador jugador=this.jugadorService.getJugadorById(4);
		String voto=FaccionesEnumerado.Mercader.toString();
		this.turnoService.anadirVoto(turno.getId(), jugador, voto);
		List<Turno> turnos=turnoService.getTurnos();
		assertThat(turnos.get(1).getVotosNeutrales()==0);
	}
	
	
	@Test
    public void cambiarVoto() throws Exception {
    	Jugador jugadorPretor=this.jugadorService.getJugadorById(3);
    	Jugador jugadorEdil1=this.jugadorService.getJugadorById(4);
		String voto=FaccionesEnumerado.Leal.toString();
		this.turnoService.anadirVoto(2, jugadorEdil1, voto);
		String votoNuevo=FaccionesEnumerado.Traidor.toString();
		VotosTurno votoTurno=this.turnoService.findVoto(2, 4);
		votoTurno.setEspiado(true);
		this.turnoService.cambiarVoto(2, jugadorPretor, jugadorEdil1.getId(), votoNuevo);
		assertThat(votoTurno.getTipoVoto()).isEqualTo(FaccionesEnumerado.Traidor);
	}
	
	//Falla porque el voto no está espiado
	@Test
    public void cambiarVotoFail() throws Exception {
    	Jugador jugadorPretor=this.jugadorService.getJugadorById(3);
    	Jugador jugadorEdil1=this.jugadorService.getJugadorById(4);
		String voto=FaccionesEnumerado.Leal.toString();
		this.turnoService.anadirVoto(2, jugadorEdil1, voto);
		String votoNuevo=FaccionesEnumerado.Traidor.toString();
		VotosTurno votoTurno=this.turnoService.findVoto(2, 4);
		votoTurno.setEspiado(false);
		assertThrows(AccessException.class, ()-> this.turnoService.cambiarVoto(2, jugadorPretor, jugadorEdil1.getId(), votoNuevo));
	}


	@Test
    void findVoto() throws Exception {
		Jugador jugadorEdil1=this.jugadorService.getJugadorById(4);
		String voto=FaccionesEnumerado.Leal.toString();
		this.turnoService.anadirVoto(2, jugadorEdil1, voto);
		VotosTurno votoTurno=this.turnoService.findVoto(2, 4);
		assertThat(votoTurno.getTipoVoto()).isEqualTo(FaccionesEnumerado.Leal);
	}
	
//	Falla al no encontrar el voto
	@Test
    void findVotoFail() throws Exception {
		assertNull(this.turnoService.findVoto(2, 3));
	}

	
	@Test
    void anadirVotoTurno() throws Exception {
		Turno turno = this.turnoService.getById(1);
		Jugador jugador=this.jugadorService.getJugadorById(4);
		this.turnoService.anadirVotoTurno(turno, jugador, FaccionesEnumerado.Traidor);
		VotosTurno vT=this.turnoService.findVoto(turno.getId(), jugador.getId());
		assertThat(vT.getTipoVoto()==FaccionesEnumerado.Traidor);
	}

//	Falla al no poder añadir el voto de tipo Mercader
	@Test
    void anadirVotoTurnoFail() throws Exception {
		Turno turno = this.turnoService.getById(1);
		Jugador jugador=this.jugadorService.getJugadorById(4);
		this.turnoService.anadirVotoTurno(turno, jugador, FaccionesEnumerado.Mercader);
		VotosTurno vT=this.turnoService.findVoto(turno.getId(), jugador.getId());
		assertThat(vT.getTurno().getVotosNeutrales()==0);
	}

	
//	@Test
//    void espiarVoto() throws Exception {
//		Partida partida = this.partidaService.findPartida(1);
//		Turno turno=this.partidaService.getTurnoActual(1);//2
//		Jugador jugadorConsul=this.jugadorService.getJugadorById(2);
//		Jugador jugadorPretor=this.jugadorService.getJugadorById(3);
//		Jugador jugadorEdil1=this.jugadorService.getJugadorById(4);
//		Jugador jugadorEdil2=this.jugadorService.getJugadorById(5);
//		String voto=FaccionesEnumerado.Traidor.toString();
//		this.turnoService.anadirVoto(turno.getId(), jugadorEdil1, voto);
//		this.turnoService.espiarVoto(partida.getId(), jugadorPretor, voto);
//		VotosTurno votoTurno=this.turnoService.findVoto(turno.getId(), jugadorConsul.getId());
//		votoTurno.setEspiado(true);
//		assertThat(turno.getPredor()).isEqualTo(jugadorPretor);
//	}
	
//	@Test
//    void espiarVotoFail() throws Exception {
	//	Partida partida = this.partidaService.findPartida(1);
	//	Turno turno=this.partidaService.getTurnoActual(1);
	//	Jugador jugadorPretor=this.jugadorService.getJugadorById(3);
	//	Jugador jugadorEdil1=this.jugadorService.getJugadorById(4);
	//	String voto=FaccionesEnumerado.Traidor.toString();
	//	this.turnoService.anadirVoto(turno.getId(), jugadorEdil1, voto);
	//	this.turnoService.espiarVoto(partida.getId(), jugadorPretor, voto);
	//	VotosTurno votoTurno=this.turnoService.findVoto(turno.getId(), 2);
	//	votoTurno.setEspiado(false);
	//	assertThat(turno.getPredor()).isEqualTo(jugadorPretor);
//	}

	
	@Test
    void getJugadoresValidosParaRol() throws Exception {
		Turno turno = this.turnoService.getById(1);
		String rol="edil";
		List<Jugador> listaJugadores = this.turnoService.getJugadoresValidosParaRol(turno.getId(), rol);
		assertThat(listaJugadores.size()==2);
	}

//	Como no existe el rol Cesar la lista esta vacía
	@Test
    void getJugadoresValidosParaRolFail() throws Exception {
		Turno turno = this.turnoService.getById(1);
		String rol="Cesar";
		List<Jugador> listaJugadores = this.turnoService.getJugadoresValidosParaRol(turno.getId(), rol);
		assertThat(listaJugadores.size()==0);
	}

	@Test
    void asignarRol() throws Exception {
		Turno turno = this.turnoService.getById(2);
		String rol="predor";
		List<Jugador> listaJugadores = this.turnoService.getJugadoresValidosParaRol(turno.getId(), rol);
		this.turnoService.asignarRol(turno.getId(), listaJugadores.get(0));
		List<Turno> turnos=turnoService.getTurnos();
		assertThat(turnos.get(1).getPredor()!=null);
	}
	
	//Salta la excepción al no ser valido el jugador
	@Test
    void asignarRolFail() throws Exception {
		Turno turno = this.turnoService.getById(2);
		Jugador jugador = this.jugadorService.getJugadorById(3);
		assertThrows(InvalidPlayerException.class, ()-> this.turnoService.asignarRol(turno.getId(), jugador));
	}
}
