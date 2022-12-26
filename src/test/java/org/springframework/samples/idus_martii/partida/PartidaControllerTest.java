package org.springframework.samples.idus_martii.partida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.idus_martii.configuration.SecurityConfiguration;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.mensaje.MensajeService;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.samples.idus_martii.turno.Estados.CambiarVotoEstado;
import org.springframework.samples.idus_martii.turno.Estados.DescubiertoAmarilloEstado;
import org.springframework.samples.idus_martii.turno.Estados.ElegirRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.EmpezarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.EspiarEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstablecerRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.RecuentoEstado;
import org.springframework.samples.idus_martii.turno.Estados.TerminarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.VotarEstado;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;


@WebMvcTest(controllers = PartidaController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                            classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class PartidaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartidaService partidaService;

    @MockBean
    private JugadorService jugadorService;
    
    @MockBean
    private RondaService rondaService;
    
    @MockBean
    private TurnoService turnoService;
    
    @MockBean
    private FaccionService faccionService;
    
    @MockBean
    private MensajeService mensajeService;
    
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
    

	private static final int PARTIDA_ID = 1;


	@BeforeEach
	void setup() {
		Lobby lobby = new Lobby();
		Partida partida = new Partida();
		partida.setLobby(lobby);
		partida.setFechaCreacion(LocalDateTime.now());
		partida.setFechaInicio(LocalDateTime.now().minusMinutes(5));
		partidaService.save(partida);
		LocalDateTime fechaCreacion = LocalDateTime.of(2022,9,18,10,30,2);
		LocalDateTime fechaInicio = LocalDateTime.of(2022,9,18,10,35,2);
		LocalDateTime fechaFin = LocalDateTime.of(2022,9,18,10,45,2);
		List<Ronda> rondas = partida.getRondas();
		Integer numeroJugadores = partida.getNumeroJugadores();
		partida.setFaccionGanadora(FaccionesEnumerado.Leal);
		partida.setFechaCreacion(fechaCreacion);
		partida.setFechaInicio(fechaInicio);
		partida.setFechaFin(fechaFin);
		partida.setRondas(rondas);
		partida.setNumeroJugadores(numeroJugadores);
		given(partidaService.getPartidas()).willReturn(Lists.newArrayList(partida));
	}

	@WithMockUser
    @Test
    public void testShowPartidasFinalizadas() throws Exception {
        mockMvc.perform(get("/partida/finalizadas")).
            andExpect(status().isOk());
    }
    @WithMockUser
    @Test
    public void testShowDetallesPartida() throws Exception {
        mockMvc.perform(get("/partida/"+PARTIDA_ID+"/detalles")).
            andExpect(status().isOk());
    }	
    @WithMockUser
    @Test
    public void testShowPartidasDisponibles() throws Exception {
        mockMvc.perform(get("/partida/disponibles")).
            andExpect(status().isOk());
    }
    @WithMockUser
    @Test
    public void testShowPartidasEnJuegos() throws Exception {
        mockMvc.perform(get("/partida/enJuego")).
            andExpect(status().isOk());
    }	

}
