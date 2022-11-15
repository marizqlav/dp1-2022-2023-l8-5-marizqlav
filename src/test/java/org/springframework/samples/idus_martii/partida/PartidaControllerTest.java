package org.springframework.samples.idus_martii.partida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.idus_martii.configuration.SecurityConfiguration;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PartidaController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class PartidaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PartidaService partidaService;
    @MockBean
    JugadorService jugadorService;
    @MockBean
    RondaService rondaService;
    @MockBean
    TurnoService turnoService;

    @WithMockUser
    @Test
    public void IniciarPartidaTest() throws Exception {
        mockMvc.perform(get("/partida/juego/{partidaId}/iniciar", 1))
            .andExpect(status().is3xxRedirection());
            
    }

    @Test
    public void JuegoTest() throws Exception {
        mockMvc.perform(get("/partida/juego/{partidaId}/"))
            .andExpect(status().isOk());
            
    }
}
