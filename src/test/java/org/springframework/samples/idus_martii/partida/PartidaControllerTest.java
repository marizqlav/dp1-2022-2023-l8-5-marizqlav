package org.springframework.samples.idus_martii.partida;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.samples.idus_martii.configuration.SecurityConfiguration;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.mensaje.MensajeService;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.samples.idus_martii.turno.Estados.CambiarVotoEstado;
import org.springframework.samples.idus_martii.turno.Estados.DescubiertoAmarilloEstado;
import org.springframework.samples.idus_martii.turno.Estados.ElegirFaccionEstado;
import org.springframework.samples.idus_martii.turno.Estados.ElegirRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.EmpezarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.EspiarEstado;
import org.springframework.samples.idus_martii.turno.Estados.EstablecerRolesEstado;
import org.springframework.samples.idus_martii.turno.Estados.RecuentoEstado;
import org.springframework.samples.idus_martii.turno.Estados.TerminarTurnoEstado;
import org.springframework.samples.idus_martii.turno.Estados.VotarEstado;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;


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
    
    @MockBean
    private ElegirFaccionEstado elegirFaccionEstado;
    
	private static final int PARTIDA_ID = 1;
	private static final int JUGADOR_ID = 1;
	private static final int PAGINA = 2;

	private Partida partidaza;

	@BeforeEach
	void setup() {
		partidaza = new Partida();
		partidaza.setId(PARTIDA_ID);
		partidaza.setFaccionGanadora(FaccionesEnumerado.Leal);
		partidaza.setNumeroJugadores(5);
		partidaza.setFechaCreacion(LocalDateTime.now());
		partidaza.setFechaInicio(LocalDateTime.now().plusMinutes(5));
		partidaza.setFechaFin(LocalDateTime.now().plusMinutes(15));
		given(partidaService.findPartida(PARTIDA_ID)).willReturn(partidaza);
	}

    @WithMockUser(value = "spring")
    @Test
    public void testShowDetallesPartida() throws Exception {
        mockMvc.perform(get("/partida/"+PARTIDA_ID+"/detalles")).
            andExpect(status().isOk());
    }	

    @WithMockUser(value = "spring")
    @Test
    public void testShowPartidasDisponibles() throws Exception {
        mockMvc.perform(get("/partida/disponibles")).
            andExpect(status().isOk());
    }
    
    @WithMockUser(value = "spring")
    @Test
    public void testShowPartidasCreadas() throws Exception {
        mockMvc.perform(get("/partida/creadas")).
            andExpect(status().isOk());
    }
    
	@WithMockUser(value = "spring")
    @Test
    public void testShowPartidasFinalizadas() throws Exception {
        mockMvc.perform(get("/partida/finalizadas")).
            andExpect(status().isOk());
    }
	
    @WithMockUser(value = "spring")
    @Test
    public void testShowPartidasEnJuegos() throws Exception {
        mockMvc.perform(get("/partida/enJuego")).
            andExpect(status().isOk());
    }	

    
    @WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
    	mockMvc.perform(get("/partida/new"))
				.andExpect(status().isOk());
	}
    

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/partida/new")
    			.param("id", "1")
				.with(csrf())
				.param("numero_jugadores", "6")
				.param("faccion_ganadora", "Traidor")
				.param("fecha_creacion", "2022-09-18 12:34:04")
				.param("fecha_inicio", "2022-09-18 12:35:02")
				.param("fecha_fin", "2022-09-18 12:49:31"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("partida"))
				.andExpect(view().name("partidas/createOrUpdatePartidaForm"));
	}
	
	 @WithMockUser(value = "spring")
	 @Test
	 public void testCancelarPartidaForm() throws Exception{
	     mockMvc.perform(get("/partida/"+PARTIDA_ID+"/cancelar")).
	     	andExpect(status().isOk())
	        .andExpect(model().attributeExists("message"))
	        .andExpect(view().name("welcome"));
	 }
	 
	 @WithMockUser(value = "spring")
	 @Test
	 public void testLobby() throws Exception{
	     mockMvc.perform(get("/partida/"+PARTIDA_ID)).
	     	andExpect(status().isOk());
	 }
	 
	 @WithMockUser(value = "spring")
	 @Test
	 public void testIniciarPartidaForm() throws Exception{
	     mockMvc.perform(get("/partida/juego/"+PARTIDA_ID+"/iniciar")).
			andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/partida/juego/"+PARTIDA_ID));
	 }
	 
	 @WithMockUser(value = "spring")
	 @Test
	 public void testGetPartidaGeneral() throws Exception{
	     mockMvc.perform(get("/partida/juego/"+PARTIDA_ID)).
	     	andExpect(status().isOk());
	 }

	 @WithMockUser(value = "spring")
	 @Test
	 public void testGetEspiarEdilForm() throws Exception{
	     mockMvc.perform(get("/partida/juego/"+PARTIDA_ID+"/espiar")).
	     	andExpect(status().isOk());
	 }

	 @WithMockUser(value = "spring")
	 @Test
	 public void testCambiarVotoForm() throws Exception{
	     mockMvc.perform(get("/partida/juego/"+PARTIDA_ID+"/cambiar")).
	     	andExpect(status().isOk());
	 }
	 
	 @WithMockUser(value = "spring")
	 @Test
	 public void testVotacionForm() throws Exception{
	     mockMvc.perform(get("/partida/juego/"+PARTIDA_ID+"/votar")).
	     	andExpect(status().isOk());
	 }
	 
	 @WithMockUser(value = "spring")
	 @Test
	 public void testElegirRolForm() throws Exception{
	     mockMvc.perform(get("/partida/juego/"+PARTIDA_ID+"/elegirrol")).
	     	andExpect(status().isOk());
	 }

	 @WithMockUser(value = "spring")
	 @Test
	 public void testElegirFaccionForm() throws Exception{
	     mockMvc.perform(get("/partida/juego/"+PARTIDA_ID+"/elegirfaccion")).
	     	andExpect(status().isOk());
	 }
	 
	 @WithMockUser(value = "spring")
	 @Test
	 public void testHistorialPartidas() throws Exception{
	     mockMvc.perform(get("/partida/partidas/jugador/"+JUGADOR_ID+"/"+PAGINA)).
	     	andExpect(status().isOk());
	 }
}