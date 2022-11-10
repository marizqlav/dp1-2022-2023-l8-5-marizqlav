package org.springframework.samples.idus_martii.jugador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class JugadorContoller {
	
	private final JugadorService jugadorService;
	@Autowired
	public JugadorContoller(JugadorService jugadorService) {
		this.jugadorService = jugadorService;
	}

}
