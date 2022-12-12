package org.springframework.samples.idus_martii.ronda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.idus_martii.configuration.SecurityConfiguration;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.Estados.*;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.assertj.core.util.Lists;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@WebMvcTest(controllers = RondaController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class RondaControllerTest {
    	

	public static final String ID_RONDA="1";
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RondaService rondaService;
    
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
    
    
    
    


	@BeforeEach
	void setup() {
		Ronda ronda = new Ronda();
		Partida partida =new Partida();
		List<Turno> turnos = ronda.getTurnos();
		ronda.setPartida(partida);
		ronda.setTurnos(turnos);
		rondaService.save(ronda);
		given(rondaService.getRondas()).willReturn(Lists.newArrayList(ronda));
	}
	
    @WithMockUser
	@Test
	public void testShowRondas() throws Exception {
	    mockMvc.perform(get("/rondas/")).
	   		andExpect(status().isOk());
	}


	//crear
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Crear ronda form")
	void testInitCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rondas/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("/rondas/createOrUpdateRondaForm"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("ronda"));
	}

	//save
	
//	@WithMockUser(value = "spring")
//	@Test
//	@DisplayName("Create new Ronda")
//	void processCreationJugadorSuccess() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/rondas/"+ID_RONDA+"/edit")
//				.with(SecurityMockMvcRequestPostProcessors.csrf())
//				.param("id", "1")
//				.with(SecurityMockMvcRequestPostProcessors.csrf()))
//		.andExpect(view().name("/rondas/createOrUpdateRondaForm"));
//	}

//	@WithMockUser(value = "spring")
//	@Test
//	@DisplayName("Cannot create new Ronda")
//	void processCreationJugadorHasErrors() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/jugadores/profile/"+ID_RONDA+"/edit")
//				.with(SecurityMockMvcRequestPostProcessors.csrf())
//				.param("id", "3"))
//		.andExpect(status().isOk())
//		.andExpect(view().name("/rondas/createOrUpdateRondaForm"));
//	}

	
//editar
//	me da un error en el controller porque saveRonda y editRonda tienen la misma url
//	@WithMockUser(value = "spring")
//	@Test
//	@DisplayName("Updating the ronda")
//	void testProcessUpdateRondaFormSuccess() throws Exception {
//		mockMvc.perform(get("/rondas/"+ ID_RONDA +"/edit")
//				.param("id", "1"))
//		.andExpect(view().name("/rondas/rondasList"));
//	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Cannot updating the ronda")
	void testProcessUpdateRondaHasErrors() throws Exception {
		mockMvc.perform(get("/rondas/"+ 2 +"/edit")
				.param("id", "1"))
		.andExpect(view().name("/rondas/createOrUpdateRondaForm"));
	}

	
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Deleting the ronda")
	void testProcessDeleteTurnoFormSuccess() throws Exception {
		mockMvc.perform(get("/rondas/"+ ID_RONDA +"/delete"))
		.andExpect(view().name("/rondas/rondasList"));
	}
}
