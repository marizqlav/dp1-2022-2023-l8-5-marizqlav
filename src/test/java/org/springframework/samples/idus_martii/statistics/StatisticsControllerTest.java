package org.springframework.samples.idus_martii.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.idus_martii.configuration.SecurityConfiguration;
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

import org.assertj.core.util.Lists;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@WebMvcTest(controllers = AchievementController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class StatisticsControllerTest {
    	
	public static final String ID_ACHIEVEMENT="10";

	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AchievementService achievementService;
    
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
		Achievement logro = new Achievement();
		logro.setName("Viciado");
		logro.setBadgeImage("https://bit.ly/certifiedGamer");
		logro.setDescription("ya estás enganchado");
		logro.setThreshold(10.0);
		achievementService.save(logro);
		given(achievementService.getAchievements()).willReturn(Lists.newArrayList(logro));
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
	
	//crear
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Crear logro form")
	void testInitCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/statistics/achievements/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("/achievements/createOrUpdateAchievementForm"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("achievement"));
	}

	//save
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Create Logro")
	void processCreationLogroSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/statistics/achievements/"+ID_ACHIEVEMENT+"/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("name", "willy")
				.param("badge_image", "https://bit.ly/certifiedGame")
				.param("threshold", "3.0")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(view().name("/achievements/createOrUpdateAchievementForm"));
	}
//
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

	//saveNewRonda
	
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Create new Logro")
	void processCreationLogroNewSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/statistics/achievements/new")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("name", "willy")
				.param("description", "Si juegas")
				.param("threshold", "3.0")
				.param("badge_image", "https://bit.ly/certifiedGamer")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(view().name("/achievements/AchievementListing"));
	}
//
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Cannot create new Logro")
	void processCreationLogroNewHasErrors() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/statistics/achievements/new")
				.param("name", "willy")
				.param("description", "Si juegas")
				.param("threshold", "3.0")
				.param("badge_image", "https://bit.ly/certifiedGamer")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(status().isOk())
		.andExpect(view().name("/achievements/AchievementListing"));
	}
	
//editar
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Updating the Logro")
	void testProcessUpdateLogroFormSuccess() throws Exception {
		mockMvc.perform(get("/statistics/achievements/"+ ID_ACHIEVEMENT +"/edit")
				.param("name", "1")
				.param("description", "Si juegas")
				.param("threshold", "3.0")
				.param("badge_image", "https://bit.ly/certifiedGamer"))
		.andExpect(view().name("/achievements/createOrUpdateAchievementForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Cannot updating the Logro")
	void testProcessUpdateLogroHasErrors() throws Exception {
		mockMvc.perform(get("/statistics/achievements/"+ 2 +"/edit")
				.param("name", "1")
				.param("description", "Si juegas")
				.param("threshold", "3.0")
				.param("badge_image", "https://bit.ly/certifiedGamer"))
		.andExpect(view().name("/achievements/createOrUpdateAchievementForm"));
	}

	
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Deleting the logro")
	void testProcessDeleteLogroFormSuccess() throws Exception {
		mockMvc.perform(get("/statistics/achievements/"+ ID_ACHIEVEMENT +"/delete"))
		.andExpect(view().name("/achievements/AchievementListing"));
	}



}
