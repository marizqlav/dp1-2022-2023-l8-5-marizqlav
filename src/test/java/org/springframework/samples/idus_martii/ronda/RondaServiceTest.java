package org.springframework.samples.idus_martii.ronda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class RondaServiceTest {

	@Autowired
	private RondaService rondaService;
	
	@Autowired
	private PartidaService partidaService;
	
	@Autowired
	private JugadorService jugadorService;
		
	@Test
	void getAllTest() {
		List<Ronda> rondas = this.rondaService.getRondas();
		assertThat(rondas.size()).isEqualTo(1);
	}
	
	@Test
    void getRondaByIdTest() throws Exception {
		Ronda ronda = this.rondaService.getById(1);
		assertThat(ronda.getId()).isEqualTo(1);
	}
	
	@Test
	public void deleteRondaTest() {
		Ronda r = new Ronda();
		Partida partida = new Partida();
		partida.setNumeroJugadores(5);
		partida.setJugador(this.jugadorService.getJugadorById(1));
		this.partidaService.save(partida);
		r.setPartida(partida);
		this.rondaService.save(r);
		Integer id = r.getId();
		try {
			this.rondaService.deleteRondaById(id);
			assertThat(this.rondaService.getById(id)).isNull();
		}catch(Exception e){
			fail("Ha ocurrido un error: "+e);
		}
		
	}
	
	@Test
	public void saveRondaTestError() {
		Ronda r = new Ronda();
		Partida partida = new Partida();
		partida.setNumeroJugadores(5);
		partida.setJugador(this.jugadorService.getJugadorById(1));
		this.partidaService.save(partida);
		r.setPartida(partida);
		try {
			this.rondaService.save(r);
		}catch(Exception e){
			fail("Ha ocurrido un error: "+e);
		}
	}
	
}
