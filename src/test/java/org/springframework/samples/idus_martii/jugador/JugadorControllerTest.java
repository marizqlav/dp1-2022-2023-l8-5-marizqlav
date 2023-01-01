package org.springframework.samples.idus_martii.jugador;

import org.springframework.samples.idus_martii.configuration.SecurityConfiguration;
import org.springframework.samples.idus_martii.turno.Estados.CambiarVotoEstado;
import org.springframework.samples.idus_martii.turno.Estados.DescubiertoAmarilloEstado;
import org.springframework.samples.idus_martii.turno.Estados.ElegirRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.EmpezarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.EspiarEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstablecerRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.RecuentoEstado;
import org.springframework.samples.idus_martii.turno.Estados.TerminarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.VotarEstado;
import org.springframework.samples.idus_martii.user.AuthoritiesService;
import org.springframework.samples.idus_martii.user.User;
import org.springframework.samples.idus_martii.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@WebMvcTest(controllers = JugadorController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class JugadorControllerTest {
    
	public static final int ID_JUGADOR=1;
	
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
    
    @MockBean
    private CambiarVotoEstado cambiarVotoEstado;
    
    @MockBean
    private DescubiertoAmarilloEstado descubiertoAmarilloEstado;
    
    @MockBean
    private RecuentoEstado recuentoEstado;

    @MockBean
    private EmpezarTurnoEstado empezarTurnoEstado;
    
    @MockBean
    private ElegirRolesEstado elegirRolesEstado;


	@BeforeEach
	void setup() {
		Jugador jugador = new Jugador();
		User user= jugador.getUser();
		jugador.setId(ID_JUGADOR);
		jugador.setUser(user);
		given(jugadorService.getAll()).willReturn(Lists.newArrayList(jugador));
		given(jugadorService.getJugadorById(jugador.getId())).willReturn(jugador);
		given(jugadorService.getUserByJugador(jugador)).willReturn(jugador.getUser());
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
	
	@WithMockUser
    @Test
    @DisplayName("InitFindForm the jugador")
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/jugadores/find"))
				.andExpect(status().isOk());
	}
////Da error
////	@WithMockUser
////    @Test
////    @DisplayName("processFindForm the jugador")
////	void testProcessFindFormSuccess() throws Exception {
////		given(this.jugadorService.getJugadorByUsername("")).willReturn(Lists.newArrayList(Jose, new Jugador()));
////		mockMvc.perform(get("/jugadores").param("user", "Alex"))
////				.andExpect(status().isOk())
////				.andExpect(view().name("/jugadores/jugadoresList"));
////	}
//	
	@WithMockUser
	@Test
	@DisplayName("Crear Jugador form")
	void testInitCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("jugadores/createOrUpdateJugadorForm"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("jugador"));
	}
	
//	saveJugador
	@WithMockUser
	@Test
	@DisplayName("Create new Jugador")
	void testProcessCreationJugadorSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/jugadores/profile/"+ID_JUGADOR+"/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
//				.param("user", "Alex")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/jugadores/profile/"+ID_JUGADOR));

	}
//
//	
//	@WithMockUser
//	@Test
//	@DisplayName("Cannot create new Jugador")
//	void testProcessCreationJugadorHasErrors() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/jugadores/profile/"+ID_JUGADOR+"/edit")
//				.with(SecurityMockMvcRequestPostProcessors.csrf())
//				.param("user", "null"))
//		.andExpect(status().isOk())
//		.andExpect(view().name("jugadores/createOrUpdateJugadorForm"));
//	}
//	
//	@WithMockUser
//	@Test
//	@DisplayName("Updating the jugador")
//	void testProcessUpdateRoundFormSuccess() throws Exception {
//		mockMvc.perform(get("/jugadores/profile/" + ID_JUGADOR+"/edit")
//				.param("user", "Ale"))
//		.andExpect(view().name("jugadores/createOrUpdateJugadorForm"));
//	}
//
//	@WithMockUser
//	@Test
//	@DisplayName("Cannot updating the jugador")
//	void testProcessUpdateRoundHasErrors() throws Exception {
//		mockMvc.perform(get("/jugadores/profile/" + 2+"/edit")
//				.param("user", "Ale"))
//		.andExpect(view().name("jugadores/createOrUpdateJugadorForm"));
//	}
//
//	
//	@WithMockUser
//	@Test
//	@DisplayName("Deleting the jugador")
//	void testProcessDeleteTurnoFormSuccess() throws Exception {
//		mockMvc.perform(get("/jugadores/eliminar/" + ID_JUGADOR))
//		.andExpect(view().name("welcome"));
//	}




}
