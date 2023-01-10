package org.springframework.samples.idus_martii.statistics;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.partida.PartidaRepository;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class StatisticsServiceTest {
	@Autowired
	protected StatisticsService ss;
	
	@Autowired
	protected JugadorService js;

	
	@Test
	 public void TestEstadisticasJugador() {
		Map<FaccionesEnumerado, List<Integer>> jugador1 = this.ss.paridasGanadas(js.getJugadorById(1));
		List<Integer> listaLeal = new ArrayList<>();
		listaLeal.add(0, 1);
		listaLeal.add(1, 0);
		listaLeal.add(2,1);
		assertThat(jugador1.get(FaccionesEnumerado.Leal)).isEqualTo(listaLeal);
		
		List<Integer> listaTraidor = new ArrayList<>();
		listaTraidor.add(0, 0);
		listaTraidor.add(1, 0);
		listaTraidor.add(2,0);
		assertThat(jugador1.get(FaccionesEnumerado.Traidor)).isEqualTo(listaTraidor);
		 
		 List<Integer> listaMercader = new ArrayList<>();
		 listaMercader.add(0, 0);
		 listaMercader.add(1, 0);
		 listaMercader.add(2,0);
		 assertThat(jugador1.get(FaccionesEnumerado.Mercader)).isEqualTo(listaMercader);
		 
	 }
	
	@Test
	 public void TestPartidasTotalesJugador() {
		 int jugador4 = this.ss.partidasTotales(js.getJugadorById(4));
		 assertThat(jugador4).isEqualTo(1);
		 
	 }
	
	@Test
	 public void TestFaccionMasJugadaJugador() {
		 FaccionesEnumerado jugador2 = this.ss.faccionMasJugadaJugador(js.getJugadorById(2));
		 assertThat(jugador2).isEqualTo(FaccionesEnumerado.Mercader);
		 
	 }
	
	 @Test
	 public void TestDuracionPartidas() {
		 Map<String, Duration> jugador3 = this.ss.duracionPartidas(js.getJugadorById(3));
		 assertThat(jugador3.get("max")).isEqualTo(Duration.between(LocalTime.of(10, 34, 04), LocalTime.of(10, 49, 31)));
		 assertThat(jugador3.get("min")).isEqualTo(Duration.between(LocalTime.of(10, 34, 04), LocalTime.of(10, 49, 31)));
		 assertThat(jugador3.get("media")).isEqualTo(Duration.between(LocalTime.of(10, 34, 04), LocalTime.of(10, 49, 31)));
	 }
	 
	 @Test
	 public void TestNumJugadoresPartida() {
		 Map<String, Integer> partidas = this.ss.numJugadoresPartida();
		 assertThat(partidas.get("max")).isEqualTo(6);
		 assertThat(partidas.get("min")).isEqualTo(6);
		 assertThat(partidas.get("media")).isEqualTo(6);
		 
	 }
	 

}
