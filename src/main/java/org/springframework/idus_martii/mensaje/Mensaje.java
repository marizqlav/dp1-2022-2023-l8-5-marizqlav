package org.springframework.idus_martii.mensaje;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "mensaje")
public class Mensaje {
	
	@NotEmpty
	@NotNull
	String texto;
	
	LocalTime hora;

	@ManyToOne
	String jugador;

}