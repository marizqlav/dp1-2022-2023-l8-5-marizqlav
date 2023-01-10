package org.springframework.samples.idus_martii.faccion;





import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.expression.AccessException;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class FaccionServiceTest {
	
	@Autowired
	private FaccionService faccionService;
	
	
	
	@Test 
	public void getAllFaccionesTest() {
		//Comprueba todas las facciones
		List<Faccion> list = faccionService.getAllFacciones();
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void asignaFaccionTest() throws Exception {
	//Cogemos un jugador en una partida que todavia no ha escogido faccion
		Integer jugador1= 1;
		
		String faccionAsignada = FaccionesEnumerado.Traidor.toString();
		Integer partida = 2;
		faccionService.asignarFaccionAJugador(faccionAsignada, jugador1, partida);
		String fac = faccionService.getFaccionJugadorPartida(jugador1, partida).getFaccionSelecionada().toString();
		assertEquals( FaccionesEnumerado.Traidor.toString(), fac);
	 
	}
	
	
	@Test
	public void asignaFaccionFailTest() throws AccessException {
		//Salta la excepcion al asignar una faccion a un jugador que ya tiene asignada una
		Integer jugador1= 1;
		
		String faccionAsignada = FaccionesEnumerado.Mercader.toString();
		Integer partida = 1;
		assertThrows(AccessException.class, ()-> faccionService.asignarFaccionAJugador(faccionAsignada, jugador1, partida));
	 
	}
	
	@Test
	public void saveFaccionTest() {
		//Comrpueba que se guarda
		Faccion f = new Faccion();
		Jugador j = new Jugador();
		
		f.setFaccionPosible1(FaccionesEnumerado.Leal);
		f.setFaccionPosible2(FaccionesEnumerado.Mercader);
		f.setFaccionSelecionada(FaccionesEnumerado.Leal);
		f.setJugador(j);
		
		
		try {
			faccionService.save(f);
			
		} catch(Exception e) {
			fail("Esta excepcion no debe ser lanzada");
		}
		
	}

	@Test
	public void saveMensajeTestError() {
		//Comprueba que no se guarda
		Faccion f = null;
		assertThrows(InvalidDataAccessApiUsageException.class, ()-> faccionService.save(f));
	
	}

	

}