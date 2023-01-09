package org.springframework.samples.idus_martii.ronda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RondaRepositoryTest {
	@Autowired
	RondaRepository rondaRepository;
	
	@Test
	public void testFindAll() {
		List<Ronda> rondas = rondaRepository.findAll();
		assertNotNull(rondas, "No se han encontrado ninguna ronda");
		assertEquals(1, rondas.size(),"Faltan datos de inicializacion en la BD");
	}

}


	
