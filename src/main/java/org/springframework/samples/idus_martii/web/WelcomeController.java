package org.springframework.samples.idus_martii.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.jugador.JugadorService;
import org.springframework.samples.idus_martii.mensaje.MensajeService;
import org.springframework.samples.idus_martii.model.Person;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	JugadorService jugadorService;
	@Autowired
    public WelcomeController(JugadorService jugadorService) {

        this.jugadorService = jugadorService;

    }

	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	   
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
		Person person1 = new Person();
		Person person2 = new Person();
		Person person3 = new Person();
		Person person4 = new Person();
		Person person5 = new Person();
		person.setFirstName("Marcos");
		person.setLastName("Olmedo");
		persons.add(person);
		person1.setFirstName("Alejandro");
		person1.setLastName("Merino");
		persons.add(person1);
		person2.setFirstName("Alberto");
		person2.setLastName("Domínguez");
		persons.add(person2);
		person3.setFirstName("Mario");
		person3.setLastName("Izquierdo");
		persons.add(person3);
		person4.setFirstName("Ismael");
		person4.setLastName("Herrera");
		persons.add(person4);
		person5.setFirstName("Pablo");
		person5.setLastName("Parra");
		persons.add(person5);
		model.put("persons", persons);
		model.put("title", "Lab DP1 L8-5");
		model.put("group", "L8-5");
	    return "welcome";
	  }
	  
}
