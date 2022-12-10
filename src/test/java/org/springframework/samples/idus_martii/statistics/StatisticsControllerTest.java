package org.springframework.samples.idus_martii.statistics;

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
import org.springframework.samples.idus_martii.turno.Estados.EspiarEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstablecerRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstadoTurno;
import org.springframework.samples.idus_martii.turno.Estados.TerminarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.VotarEstado;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
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
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = AchievementController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class StatisticsControllerTest {
    	
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


}
