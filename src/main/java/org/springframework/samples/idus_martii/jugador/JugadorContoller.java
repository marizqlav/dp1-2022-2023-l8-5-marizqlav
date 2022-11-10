package org.springframework.samples.idus_martii.jugador;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.owner.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
public class JugadorContoller {
	
	private final JugadorService jugadorService;
	@Autowired
	public JugadorContoller(JugadorService jugadorService) {
		this.jugadorService = jugadorService;
	}/*
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/jugadores/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("jugador", new Owner());
		return "jugador/findJugadores";
	}*/
	
	

}
