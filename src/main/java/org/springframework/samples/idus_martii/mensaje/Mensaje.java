package org.springframework.samples.idus_martii.mensaje;

import java.time.LocalTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mensaje")
public class Mensaje extends BaseEntity{
	@NotBlank
	@Column(name = "hora")
	@DateTimeFormat(pattern = "hh:mm")
	private LocalTime hora;
	@NotBlank
	@Column(name = "texto")
	private String texto;
	
	
	// Bidireccionalidad con Jugador
	@ManyToOne(optional=false)
	@JoinColumn(name = "jugador_id")
	private Jugador jugador;
}