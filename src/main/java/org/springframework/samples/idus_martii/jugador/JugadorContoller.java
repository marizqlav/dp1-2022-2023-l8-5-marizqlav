package org.springframework.samples.idus_martii.jugador;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/jugadores")
public class JugadorContoller {
	
	private static final String VIEWS_JUGADORES_LISTING = "jugadores/jugadoresListing";
	private static final String VIEWS_USUARIO_LISTING = "jugadores/userByPlayer";
	
	private final JugadorService jugadorService;
	@Autowired
	public JugadorContoller(JugadorService jugadorService) {
		this.jugadorService = jugadorService;
	}
	
	
	



	   
	   


	

}
