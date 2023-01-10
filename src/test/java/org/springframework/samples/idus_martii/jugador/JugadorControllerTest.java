package org.springframework.samples.idus_martii.jugador;

import org.springframework.samples.idus_martii.configuration.SecurityConfiguration;
import org.springframework.samples.idus_martii.turno.Estados.CambiarVotoEstado;
import org.springframework.samples.idus_martii.turno.Estados.DescubiertoAmarilloEstado;
import org.springframework.samples.idus_martii.turno.Estados.ElegirFaccionEstado;
import org.springframework.samples.idus_martii.turno.Estados.ElegirRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.EmpezarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.EspiarEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstablecerRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.FinalPartidaEstado;
import org.springframework.samples.idus_martii.turno.Estados.RecuentoEstado;
import org.springframework.samples.idus_martii.turno.Estados.TerminarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.VotarEstado;
import org.springframework.samples.idus_martii.user.AuthoritiesService;
import org.springframework.samples.idus_martii.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



@WebMvcTest(controllers = JugadorController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class JugadorControllerTest {
    
	public static final int ID_JUGADOR=1;
	public static final int ID_JUGADOR_AMIGO=3;
	public static final int NUMERO_PAGINA=3;
	
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

    @MockBean
    private ElegirFaccionEstado elegirFaccionEstado;

    @MockBean
    private FinalPartidaEstado finalPartidaEstado;
    
    private Jugador carlos;
    
	@BeforeEach
	void setup() {
		carlos = new Jugador();
		carlos.setId(ID_JUGADOR);
		jugadorService.save(carlos);
		given(jugadorService.getJugadorById(ID_JUGADOR)).willReturn(carlos);
		given(jugadorService.getUserByJugador(carlos)).willReturn(carlos.getUser());
	}

	@WithMockUser(value = "spring")
	@Test
	public void testShowJugador() throws Exception {
	    mockMvc.perform(get("/"+ID_JUGADOR+"/user")).
	   		andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	public void testShowPerfilJugador() throws Exception {
	    mockMvc.perform(get("/jugadores/profile/"+ID_JUGADOR)).
	   		andExpect(status().isOk());
	}
	
	
	 @WithMockUser(value = "spring")
	 @Test
	 public void testIrPerfilJugador() throws Exception{
	     mockMvc.perform(get("/jugadores/profile/nombre/"+jugadorService.getJugadorById(ID_JUGADOR))).
	     	andExpect(status().isOk());
	 }
	 
	 @WithMockUser(value = "spring")
	 @Test
	 public void testPeticionAmistad() throws Exception{
	     mockMvc.perform(get("/jugadores/amigos/"+ ID_JUGADOR+ "/"+ID_JUGADOR_AMIGO)).
	     	andExpect(status().isOk());
	 }	
	 
	@WithMockUser(value = "spring")
    @Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/jugadores/find"))
				.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessFindForm() throws Exception {
		mockMvc.perform(get("/jugadores"))
				.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessFindFormPaginated() throws Exception {
		mockMvc.perform(get("/jugadores/"+NUMERO_PAGINA))
				.andExpect(status().isOk());
	}


	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("jugador"))
		.andExpect(view().name("jugadores/createOrUpdateJugadorForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationForm() throws Exception {
		mockMvc.perform(post("/new")
			.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
	}

	@WithMockUser(value = "spring")
    @Test
	void testPeticiones() throws Exception {
		mockMvc.perform(get("/jugadores/"+NUMERO_PAGINA))
				.andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
    @Test
	void testRechazar() throws Exception {
		mockMvc.perform(get("/jugadores/peticiones/rechazar/"+ID_JUGADOR_AMIGO))
				.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testAmigos() throws Exception {
		mockMvc.perform(get("/jugadores/amigos"))
				.andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
    @Test
	void testDeleteAmigo() throws Exception {
		mockMvc.perform(get("/jugadores/amigos/eliminar/"+ID_JUGADOR))
				.andExpect(status().isOk());
	}

	
	@WithMockUser(value = "spring")
	@Test
	void testEditJugadorForm() throws Exception {
		mockMvc.perform(get("/jugadores/profile/"+ID_JUGADOR+"/edit"))
				.andExpect(status().isOk());
	}	
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationJugadorSuccess() throws Exception {
		mockMvc.perform(post("/jugadores/profile/"+5+"/edit")
				.with(csrf())
				)
				.andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessDeleteTurnoFormSuccess() throws Exception {
		mockMvc.perform(get("/jugadores/eliminar/" + ID_JUGADOR))
				.andExpect(view().name("welcome"));
	}
}
