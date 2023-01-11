package org.springframework.samples.idus_martii.mensaje;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MensajeRepositoryTest {
	
	@Autowired
	MensajeRepository mensajeRepository;
	
	@Test
	public void testFindAll() {
		List<Mensaje> mensajes = mensajeRepository.findAll();
		assertNotNull(mensajes, "Mensaje de prueba 1");
		assertEquals(2, mensajes.size(),"Faltan datos de inicializacion en la BD");
	}
	
	@Test
	public void testFindMensajesByPartidaId() {
		List<Mensaje> listaMensajes=mensajeRepository.findMensajesByPartidaId(1);
		assertNotNull(listaMensajes);
		assertTrue(listaMensajes.isEmpty());
	}
}


	
