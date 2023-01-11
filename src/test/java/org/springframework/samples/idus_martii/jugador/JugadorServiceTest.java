package org.springframework.samples.idus_martii.jugador;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.samples.idus_martii.user.User;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class JugadorServiceTest {
	
	@Autowired
	private JugadorService jugadorService;

	
	@Test
	void getAllTest() {
		List<Jugador> jugadores = this.jugadorService.getAll();
		assertThat(jugadores.size()).isEqualTo(6);
		Jugador[] jugadorArray = jugadores.toArray(new Jugador[jugadores.size()]);
		assertThat(jugadorArray[0].getId()).isEqualTo(1);
		assertThat(jugadorArray[0].getUsername()).isEqualTo("Ale");
		
	}
	
	@Test
	void getByNameTest(){
		Jugador jugadores = this.jugadorService.getByName("albdomrui");
		assertThat(jugadores.getId()).isEqualTo(5);
		assertThat(jugadores.getUsername()).isEqualTo("albdomrui");
	}
	
	@Test
	void getJugadorByIdTest() {
		Jugador jugador = this.jugadorService.getJugadorById(4);
		assertThat(jugador.getId()).isEqualTo(4);
		assertThat(jugador.getUsername()).isEqualTo("marolmmar1");

	}
	
	
	
	void getUserByJugadorTest() {
		Jugador jugador = this.jugadorService.getJugadorById(4);
		User usuario = this.jugadorService.getUserByJugador(jugador);
		assertThat(usuario.getUsername()).isEqualTo("marolmmar1");
		assertThat(usuario.getName()).isEqualTo("Marcos");
	}
	
	
	@Test
	void getJugadorByUsernameTest() {
		List<Jugador> jugadores = this.jugadorService.getJugadorByUsername("marizqlav");
		assertThat(jugadores.get(0).getId()).isEqualTo(3);
		assertThat(jugadores.get(0).getUsername()).isEqualTo("marizqlav");

	}
	
	
	@Test
	void getPlayersPaginatedTest() {
		List<List<Jugador>> paginacion = this.jugadorService.getPlayersPaginated(this.jugadorService.getAll());
		assertThat(paginacion.size()).isEqualTo(3);
		assertThat(paginacion.get(0).size()).isEqualTo(2);
		assertThat(paginacion.get(1).size()).isEqualTo(2);
		assertThat(paginacion.get(2).size()).isEqualTo(2);
		assertThat(paginacion.get(0).get(0).getId()).isEqualTo(1);
		assertThat(paginacion.get(1).get(0).getId()).isEqualTo(3);
		assertThat(paginacion.get(2).get(0).getId()).isEqualTo(5);
	}
	
	@Test
	void  anadirAmigoTest() {
		this.jugadorService.anadirAmigo(4, 6);
		this.jugadorService.anadirAmigo(6, 4);
		Jugador jugador4 = this.jugadorService.getJugadorById(4);
		Jugador jugador6 = this.jugadorService.getJugadorById(6);
		assertThat(jugador4.setAmigos).contains(jugador6);
		assertThat(jugador6.setAmigos).contains(jugador4);

	}
	
	@Test
	void sonAmigosTest() {
		Integer sonAmigos = this.jugadorService.sonAmigos(1, 2);
		assertThat(sonAmigos).isNotNull();
		assertThat(sonAmigos).isEqualTo(2);
	}
	
	@Test
	void getAmigosTest() {
		List<Jugador> amigos = this.jugadorService.getAmigos(1);
		assertThat(amigos.size()).isEqualTo(1);
		assertThat(amigos.get(0).getId()).isEqualTo(2);
	}
	
	@Test
	void noSonAmigosTest(){
		Boolean noSonAmigos = this.jugadorService.noSonAmigos(2, 6);
		assertThat(noSonAmigos).isEqualTo(true);
	}
	
	@Test
	void deleteAmigoTest() {
		this.jugadorService.deleteAmigo(2, 1);
		Jugador jugador2 = this.jugadorService.getJugadorById(2);
		Jugador jugador1 = this.jugadorService.getJugadorById(1);
		assertThat(jugador2.setAmigos).isEmpty();
		assertThat(jugador1.setAmigos).isEmpty();
	}
	 
	@Test
	void deleteJugadorByIdTest() {
		this.jugadorService.deleteJugadorById(5);
		assertThat(this.jugadorService.getJugadorById(5)).isNull();
	}
	
	
	@Test
	void getpeticionesAmistadJugadorTest() {
		this.jugadorService.anadirAmigo(4, 3);
		List<Jugador> petAmistad = this.jugadorService.getpeticionesAmistadJugador(3);
		Jugador jugador4 = this.jugadorService.getJugadorById(4);
		assertThat(petAmistad).contains(jugador4);
		
	}
	
	@Test
	void rechazarPeticionTest() {
		this.jugadorService.anadirAmigo(4, 3);
		List<Jugador> petAmistad = this.jugadorService.getpeticionesAmistadJugador(3);
		Jugador jugador4 = this.jugadorService.getJugadorById(4);
		Jugador jugador3 = this.jugadorService.getJugadorById(3);
		assertThat(petAmistad).contains(jugador4); 
		this.jugadorService.rechazarPeticion(3, 4);
		assertThat(jugador3.setAmigos).doesNotContain(jugador3);
	}
	
	

}
