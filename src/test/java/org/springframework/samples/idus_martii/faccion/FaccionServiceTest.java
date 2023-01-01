package org.springframework.samples.idus_martii.faccion;





import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.idus_martii.jugador.Jugador;


@DataJpaTest
public class FaccionServiceTest {
	
	FaccionService faccionService;
	FaccionRepository faccionRepository;


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
	void testgetAllFacciones() {
		List<Faccion> listFacc = faccionService.getAllFacciones();
		assertNotNull(listFacc);
		assertFalse(listFacc.isEmpty());
	}
	
	

}
