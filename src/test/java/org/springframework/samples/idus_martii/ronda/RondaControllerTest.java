package org.springframework.samples.idus_martii.ronda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.idus_martii.configuration.SecurityConfiguration;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.Estados.EspiarEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstablecerRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.TerminarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.VotarEstado;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.List;

import org.assertj.core.util.Lists;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@WebMvcTest(controllers = RondaController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class RondaControllerTest {
    	
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

}
