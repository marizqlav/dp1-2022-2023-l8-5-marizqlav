package org.springframework.samples.idus_martii.mensaje;


import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
@ExtendWith(MockitoExtension.class)
public class MensajeServiceTest {
	
	@Autowired
	MensajeService mensajeService;

	@Mock
	MensajeRepository mrepo;
	
	//Comprobamos si solo hay un elemento en la lista porque anteriormente habia dos
	@Test
	public void deleteMensajeTest() {
		this.mensajeService.deleteMensajeById(1);
		List<Mensaje> mensajes = mensajeService.getMensajes();
		assertTrue(mensajes.size()== 1);
	
	}
	
	//Se intentan borrar mensajes que no existen
	@Test
	public void deleteMensajeFailTest() {
		//Se intentan borrar mensajes que no existen
		assertThrows(EmptyResultDataAccessException.class, ()-> mensajeService.deleteMensajeById(-1));
		assertThrows(EmptyResultDataAccessException.class, ()-> mensajeService.deleteMensajeById(4));
	
	}

	//comprueba que se guarda el mensaje
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
	
	//Comprueba que no se guarda
	@Test
	public void saveMensajeTestError() {
		Mensaje m = null;
		assertThrows(InvalidDataAccessApiUsageException.class, ()-> mensajeService.save(m));
	}
	
	//Comprueba que el texto es igual que el seleccionado de ese id de mensaje
	@Test
	public void getMensajeByIdTest() {
		assertEquals("Mensaje de prueba 1", mensajeService.getById(1).getTexto());
	}

	//Comprueba que el texto NO es igual que el seleccionado de ese id de mensaje
	@Test
	public void getMensajeByIdFailTest() {
		assertNotEquals("Hola", mensajeService.getById(1).getTexto());
	}
}