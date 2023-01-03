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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@WebMvcTest(controllers = RondaController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class RondaControllerTest {
    	

	public static final int ID_RONDA= 1;
	
	private Ronda rondaza;
	
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
    
    @MockBean
    private ElegirRolesEstado elegirRolesEstado;
    
    @MockBean
    private EstadoTurnoConverter estadoTurnoConverter;
   
	@BeforeEach
	void setup() {
		rondaza = new Ronda();
		Partida partida =new Partida();
		rondaza.setId(ID_RONDA);
		List<Turno> turnos = rondaza.getTurnos();
		rondaza.setPartida(partida);
		rondaza.setTurnos(turnos);
		rondaService.save(rondaza);
		given(rondaService.getById(ID_RONDA)).willReturn(rondaza);
	}
	
    @WithMockUser
	@Test
	public void testShowRondas() throws Exception {
	    mockMvc.perform(get("/rondas/")).
	   		andExpect(status().isOk());
	}

    
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Deleting the ronda")
	void testDeleteRondaForm() throws Exception {
		mockMvc.perform(get("/rondas/"+ ID_RONDA +"/delete"))
			.andExpect(view().name("/rondas/rondasList"));
	}

	
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Edit the ronda")
	void testEditRondaForm() throws Exception {
		mockMvc.perform(get("/rondas/"+ ID_RONDA +"/edit"))
			.andExpect(status().isOk())
			.andExpect(view().name("/rondas/createOrUpdateRondaForm"));
	}

	
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Save Ronda")
	void processSaveRondaSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/rondas/"+ID_RONDA+"/edit")
				.with(csrf()))
				.andExpect(view().name("/rondas/rondasList"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Crear ronda form")
	void testInitCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rondas/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("ronda"))
		.andExpect(MockMvcResultMatchers.view().name("/rondas/createOrUpdateRondaForm"));
	}


	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Save new Ronda")
	void processSaveRondaNewSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/rondas/new")
				.with(csrf()))
				.andExpect(view().name("/rondas/rondasList"));
	}

}
