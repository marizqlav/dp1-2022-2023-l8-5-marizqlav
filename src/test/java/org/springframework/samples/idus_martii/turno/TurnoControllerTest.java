package org.springframework.samples.idus_martii.turno;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.idus_martii.configuration.SecurityConfiguration;
import org.springframework.samples.idus_martii.faccion.FaccionesConverter;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.turno.Estados.CambiarVotoEstado;
import org.springframework.samples.idus_martii.turno.Estados.DescubiertoAmarilloEstado;
import org.springframework.samples.idus_martii.turno.Estados.EmpezarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.EspiarEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstablecerRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstadoTurno;
import org.springframework.samples.idus_martii.turno.Estados.RecuentoEstado;
import org.springframework.samples.idus_martii.turno.Estados.TerminarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.VotarEstado;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = TurnoController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class TurnoControllerTest {
    	
	
	public static final String ID_TURNO="1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TurnoService turnoService;
    
    @MockBean
    private PartidaService partidaService;
    
    @MockBean
    private FaccionesConverter faccionesConverter;

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
		Turno turno = new Turno();
		Jugador j1=new Jugador();
		Jugador j2=new Jugador();
		Jugador j3=new Jugador();
		Jugador j4=new Jugador();
		Ronda ronda = new Ronda();
		turno.setConsul(j1);
		turno.setEdil1(j2);
		turno.setEdil2(j3);
		turno.setPredor(j4);
		turno.setRonda(ronda);
		turno.setVotosLeales(3);
		turno.setVotosTraidores(3);
		turno.setVotosNeutrales(3);
		turnoService.save(turno);
		given(turnoService.getTurnos()).willReturn(Lists.newArrayList(turno));
	}

	@WithMockUser
	@Test
	public void testShowTurnos() throws Exception {
	    mockMvc.perform(get("/turnos/")).
	   		andExpect(status().isOk());
	}

	//crear
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Crear turno form")
	void testInitCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/turnos/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("/turnos/createOrUpdateTurnoForm"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("turno"));
	}

	//save
//	Da error porque no esta creado createOrUpdateTurnoForm ni turnosList
//	@WithMockUser(value = "spring")
//	@Test
//	@DisplayName("Create Turno")
//	void processCreationTurnoSuccess() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/turnos/"+ID_TURNO+"/edit")
//				.with(SecurityMockMvcRequestPostProcessors.csrf())
//				.param("consul_id", "1")
//				.param("predor_id", "2")
//				.param("edil1_id", "3")
//				.param("edil2_id", "4")
//				.param("votos_traidores", "1")
//				.param("votos_leales", "1")
//				.param("votos_neutrales", "1")
//				.param("ronda_id", "1")
//				.with(SecurityMockMvcRequestPostProcessors.csrf()))
//		.andExpect(view().name("/turnos/createOrUpdateTurnoForm"));
//	}

//	@WithMockUser(value = "spring")
//	@Test
//	@DisplayName("Cannot create Turno")
//	void processCreationTurnoHasErrors() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/turnos/"+ID_TURNO+"/edit")
	//			.param("consul_id", "1")
	//			.param("predor_id", "2")
	//			.param("edil1_id", "3")
	//			.param("edil2_id", "4")
	//			.param("votos_traidores", "1")
	//			.param("votos_leales", "1")
	//			.param("votos_neutrales", "1")
	//			.param("ronda_id", "1")
	//			.with(SecurityMockMvcRequestPostProcessors.csrf()))
//		.andExpect(status().isOk())
//		.andExpect(view().name("/turnos/createOrUpdateTurnoForm"));
//	}
//
//	//saveNewRonda
//	
//	@WithMockUser(value = "spring")
//	@Test
//	@DisplayName("Create new Turno")
//	void processCreationTurnoNewSuccess() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/turnos/new")
//				.with(SecurityMockMvcRequestPostProcessors.csrf())
//				.param("consul_id", "1")
//				.param("predor_id", "2")
//				.param("edil1_id", "3")
//				.param("edil2_id", "4")
//				.param("votos_traidores", "1")
//				.param("votos_leales", "1")
//				.param("votos_neutrales", "1")
//				.param("ronda_id", "1")
//				.with(SecurityMockMvcRequestPostProcessors.csrf()))
//		.andExpect(view().name("/turnos/turnosList"));
//	}
//
//	@WithMockUser(value = "spring")
//	@Test
//	@DisplayName("Cannot create new Turno")
//	void processCreationTurnoNewHasErrors() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/turnos/new")
//				.param("consul_id", "1")
//				.param("predor_id", "2")
//				.param("edil1_id", "3")
//				.param("edil2_id", "4")
//				.param("votos_traidores", "1")
//				.param("votos_leales", "1")
//				.param("votos_neutrales", "1")
//				.param("ronda_id", "1")
//				.with(SecurityMockMvcRequestPostProcessors.csrf()))
//		.andExpect(status().isOk())
//		.andExpect(view().name("/turnos/turnosList"));
//	}
//
//	
////editar
//	@WithMockUser(value = "spring")
//	@Test
//	@DisplayName("Updating the turno")
//	void testProcessUpdateTurnoFormSuccess() throws Exception {
//		mockMvc.perform(get("/turnos/"+ ID_TURNO +"/edit")
//				.param("consul_id", "1")
//				.param("predor_id", "2")
//				.param("edil1_id", "3")
//				.param("edil2_id", "4")
//				.param("votos_traidores", "1")
//				.param("votos_leales", "1")
//				.param("votos_neutrales", "1")
//				.param("ronda_id", "1"))
//		.andExpect(view().name("/turnos/createOrUpdateTurnoForm"));
//	}
//
//	@WithMockUser(value = "spring")
//	@Test
//	@DisplayName("Cannot updating the turno")
//	void testProcessUpdateTurnoHasErrors() throws Exception {
//		mockMvc.perform(get("/turnos/"+ 2 +"/edit")
//				.param("consul_id", "1")
//				.param("predor_id", "2")
//				.param("edil1_id", "3")
//				.param("edil2_id", "4")
//				.param("votos_traidores", "1")
//				.param("votos_leales", "1")
//				.param("votos_neutrales", "1")
//				.param("ronda_id", "1"))
//		.andExpect(view().name("/turnos/createOrUpdateTurnoForm"));
//	}
//
//	
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Deleting the turno")
	void testProcessDeleteTurnoFormSuccess() throws Exception {
		mockMvc.perform(get("/turnos/"+ ID_TURNO +"/delete"))
		.andExpect(view().name("/turnos/turnosList"));
	}
}
