package org.springframework.samples.idus_martii.mensaje;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.idus_martii.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Mensaje extends BaseEntity{
	
	@NotEmpty
	@NotNull
	String texto;
	
	LocalTime hora;

	//@ManyToOne
	String nombreJugador;

}