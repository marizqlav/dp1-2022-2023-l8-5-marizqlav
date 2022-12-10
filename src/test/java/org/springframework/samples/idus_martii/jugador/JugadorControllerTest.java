package org.springframework.samples.idus_martii.jugador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.idus_martii.configuration.SecurityConfiguration;
import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.statistics.Achievement;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.samples.idus_martii.turno.Estados.EspiarEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstablecerRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.TerminarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.VotarEstado;
import org.springframework.samples.idus_martii.user.AuthoritiesService;
import org.springframework.samples.idus_martii.user.User;
import org.springframework.samples.idus_martii.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@WebMvcTest(controllers = JugadorController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class JugadorControllerTest {
    
	public static final String ID_JUGADOR="1";
	
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private JugadorService jugadorService;
    
    @MockBean
    private UserService userService;
	
    @MockBean
    private AuthoritiesService authoritiesService;

    @MockBean
    private EstablecerRolesEstado establecerRolesEstado;
    
    @MockBean
    private VotarEstado votarEstado;
    
    @MockBean
    private EspiarEstado espiarEstado;
    
    @MockBean
    private TerminarTurnoEstado terminarTurnoEstado;


	@BeforeEach
	void setup() {
		Jugador jugador = new Jugador();
		User usuario = jugador.getUser();
		jugador.setId(1);
		jugador.setUser(usuario);
		jugadorService.save(jugador);
		given(jugadorService.getAll()).willReturn(Lists.newArrayList(jugador));
	}

	@WithMockUser
	@Test
	public void testShowJugador() throws Exception {
	    mockMvc.perform(get("/"+ID_JUGADOR+"/user")).
	   		andExpect(status().isOk());
	}
	@WithMockUser
	@Test
	public void testShowPerfilJugador() throws Exception {
	    mockMvc.perform(get("/jugadores/profile/"+ID_JUGADOR)).
	   		andExpect(status().isOk());
	}

}
