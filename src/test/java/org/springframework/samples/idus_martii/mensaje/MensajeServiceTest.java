package org.springframework.samples.idus_martii.mensaje;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//@ExtendWith(MockitoExtension.class)
public class MensajeServiceTest {
	
	@Autowired
	MensajeService mensajeService;
	
	/*
	@Mock
	MensajeRepository mrepo;
	
	@Test
	public void saveMensajeTest() {
		Mensaje m = new Mensaje();
		Jugador jugador = new Jugador();
		Partida partida = new Partida();
		m.setHora(LocalTime.NOON);
		m.setTexto("Hola");
		m.setJugador(jugador);
		m.setPartida(partida);
		
		MensajeService mensajeService = new MensajeService(mrepo);
		try {
		mensajeService.save(m);
		
	}catch(Exception e){
		fail("No tendría que saltar excepcion");
	}
}
	
	@Test
	public void saveMensajeTestError() {
		Mensaje m = new Mensaje();
		Jugador jugador = new Jugador();
		Partida partida = new Partida();
		m.setHora(LocalTime.NOON);
		m.setTexto("Hola");
		m.setJugador(jugador);
		m.setPartida(partida);
		
		MensajeService mensajeService = new MensajeService(null);
		try {
		mensajeService.save(m);
		
	}catch(Exception e){
		fail("No tendría que saltar excepcion");
	}
		*/
	
	
	@Test
	public void deleteMensajeTest() {
		
		Mensaje m = new Mensaje();
		Jugador jugador = new Jugador();
		Partida partida = new Partida();
		m.setHora(LocalTime.NOON);
		m.setTexto("Hola");
		m.setJugador(jugador);
		m.setPartida(partida);
		
		mensajeService.save(m);
		Integer id = m.getId();
		
		try {
		mensajeService.deleteMensajeById(id);
		assertNull(m);
		
		
		}catch(Exception e){
			fail("No tendría que saltar excepcion");
		}
		
		
}

	
	
}
