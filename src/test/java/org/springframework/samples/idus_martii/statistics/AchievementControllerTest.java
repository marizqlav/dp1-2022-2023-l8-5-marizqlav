package org.springframework.samples.idus_martii.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.idus_martii.configuration.SecurityConfiguration;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.turno.Estados.*;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = AchievementController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class AchievementControllerTest {
    	
	public static final int ID_ACHIEVEMENT=1;
	public static final int ID_JUGADOR=1;
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AchievementService achievementService;
    
    @MockBean
    private JugadorService jugadorService;
    
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
    
	@BeforeEach
	void setup() {
		Achievement logro = new Achievement();
		logro.setId(ID_ACHIEVEMENT);
		logro.setName("Abel");
		logro.setBadgeImage("https://bit.ly/certifiedGamer");
		logro.setDescription("ya estás enganchado");
		logro.setThreshold(10.0);
		achievementService.save(logro);
		given(achievementService.getById(ID_ACHIEVEMENT)).willReturn(logro);
	}

	@WithMockUser
	@Test
	public void testShowPlayerAchievements() throws Exception {
	    mockMvc.perform(get("/statistics/achievements/")).
	   		andExpect(status().isOk());
	}
	
	@WithMockUser
	@Test
	public void testShowAchievements() throws Exception {
	    mockMvc.perform(get("/statistics/achievements/manageAchievements")).
	   		andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Deleting the logro")
	void testProcessDeleteLogroForm() throws Exception {
		mockMvc.perform(get("/statistics/achievements/"+ ID_ACHIEVEMENT +"/delete"))
		.andExpect(view().name("/achievements/AchievementListing"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Edit the logro")
	void testEditRondaForm() throws Exception {
		mockMvc.perform(get("/statistics/achievements/"+ID_ACHIEVEMENT+"/edit"))
			.andExpect(status().isOk())
			.andExpect(view().name("/achievements/createOrUpdateAchievementForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Save Logro")
	void processSaveLogroSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/statistics/achievements/"+ID_ACHIEVEMENT+"/edit")
				.with(csrf())
				.param("name", "willy")
				.param("badge_image", "https://bit.ly/certifiedGame")
				.param("description", "Si juegas")
				.param("threshold", "3.0")
				.with(csrf()))
				.andExpect(view().name("/achievements/AchievementListing"));
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Cannot create Logro")
	void processCreationLogroHasErrors() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/statistics/achievements/"+ID_ACHIEVEMENT+"/edit")
				.param("name", "willy")
				.param("threshold", "3.0")
				.param("badge_image", "https://bit.ly/certifiedGamer")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(status().isOk())
		.andExpect(view().name("/achievements/createOrUpdateAchievementForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Crear logro form")
	void testInitCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/statistics/achievements/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("achievement"))
		.andExpect(MockMvcResultMatchers.view().name("/achievements/createOrUpdateAchievementForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Create new Logro")
	void processCreationLogroNewSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/statistics/achievements/new")
				.with(csrf())
				.param("name", "willy")
				.param("description", "Si juegas")
				.param("threshold", "3.0")
				.param("badge_image", "https://bit.ly/certifiedMatch")
				.with(csrf()))
				.andExpect(view().name("/achievements/AchievementListing"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Logros jugador")
	void testAschievementsPlayerForm() throws Exception {
		mockMvc.perform(get("/statistics/achievements/jugador/"+ ID_JUGADOR))
			.andExpect(status().isOk())
			.andExpect(view().name("/achievements/AchievementsJugador"));
	}

	
}


