package org.springframework.samples.idus_martii.faccion;





import static org.junit.jupiter.api.Assertions.fail;

import java.security.Provider.Service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.expression.AccessException;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.partida.PartidaService;



@DataJpaTest
public class FaccionServiceTest {
	/*
	@Autowired
	private FaccionService faccionService;
	
	@Autowired
	private JugadorService jugadorService;
	
	@Autowired
	private PartidaService partidaService;
	
	

	@Test
	public void testSaveFaccion() {
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
	public void testAsignaFaccion() {
		
		Integer jugador1= jugadorService.getJugadorById(1).getId();
		String faccionAsignada = FaccionesEnumerado.Leal.toString();
		Integer partida1 = partidaService.findPartida(jugador1).getId();

	 try {
		faccionService.asignarFaccionAJugador(faccionAsignada, jugador1, partida1);
	} catch (AccessException e) {
		// TODO Auto-generated catch block
		fail("La facción debería guardarse");
	}
	 
	 
	}

*/
}

