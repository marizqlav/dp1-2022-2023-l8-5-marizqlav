package org.springframework.samples.idus_martii.jugador;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.idus_martii.user.AuthoritiesService;
import org.springframework.samples.idus_martii.user.User;
import org.springframework.samples.idus_martii.user.UserService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
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
		
	}

}
