package org.springframework.samples.idus_martii.turno;

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
import org.springframework.samples.idus_martii.turno.Estados.ElegirRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.EmpezarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.EspiarEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstablecerRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstadoTurnoConverter;
import org.springframework.samples.idus_martii.turno.Estados.FinalPartidaEstado;
import org.springframework.samples.idus_martii.turno.Estados.RecuentoEstado;
import org.springframework.samples.idus_martii.turno.Estados.TerminarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.VotarEstado;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@WebMvcTest(controllers = TurnoController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class TurnoControllerTest {
    	
	
	public static final int ID_TURNO=1;
	
	private Turno turnazo;

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
    
    @MockBean
    private ElegirRolesEstado elegirRolesEstado;
    
    @MockBean
    private EstadoTurnoConverter estadoTurnoConverter;

    @MockBean
    private FinalPartidaEstado finalPartidaEstado;

	@BeforeEach
	void setup() {
		turnazo = new Turno();
		Jugador j1=new Jugador();
		Jugador j2=new Jugador();
		Jugador j3=new Jugador();
		Jugador j4=new Jugador();
		Ronda ronda = new Ronda();
		turnazo.setId(ID_TURNO);
		turnazo.setConsul(j1);
		turnazo.setEdil1(j2);
		turnazo.setEdil2(j3);
		turnazo.setPredor(j4);
		turnazo.setRonda(ronda);
		turnazo.setVotosLeales(3);
		turnazo.setVotosTraidores(3);
		turnazo.setVotosNeutrales(3);
		turnoService.save(turnazo);
		given(turnoService.getById(ID_TURNO)).willReturn(turnazo);
	}

	@WithMockUser(value = "spring")
	@Test
	public void testShowTurnos() throws Exception {
	    mockMvc.perform(get("/turnos/")).
	   		andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessDeleteTurno() throws Exception {
		mockMvc.perform(get("/turnos/"+ ID_TURNO +"/delete"))
		.andExpect(view().name("/turnos/turnosList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testEditTurno() throws Exception {
		mockMvc.perform(get("/turnos/"+ ID_TURNO +"/edit")
				.param("consul_id", "1")
				.param("predor_id", "2")
				.param("edil1_id", "3")
				.param("edil2_id", "4")
				.param("votos_traidores", "1")
				.param("votos_leales", "1")
				.param("votos_neutrales", "1")
				.param("ronda_id", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("/turnos/createOrUpdateTurnoForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessSaveTurno() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/turnos/"+ID_TURNO+"/edit")
				.with(csrf())
				.param("consul_id", "1")
				.param("predor_id", "2")
				.param("edil1_id", "3")
				.param("edil2_id", "4")
				.param("votos_traidores", "1")
				.param("votos_leales", "1")
				.param("votos_neutrales", "1")
				.param("ronda_id", "1")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("/turnos/turnosList"));
	}
	

	@WithMockUser(value = "spring")
	@Test
	void testInitCreation() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/turnos/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("turno"))
		.andExpect(view().name("/turnos/createOrUpdateTurnoForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessSaveNewTurno() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/turnos/new")
				.with(csrf())
				.param("consul_id", "1")
				.param("predor_id", "2")
				.param("edil1_id", "3")
				.param("edil2_id", "4")
				.param("votos_traidores", "1")
				.param("votos_leales", "1")
				.param("votos_neutrales", "1")
				.param("ronda_id", "1")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("/turnos/turnosList"));
	}

	
}
