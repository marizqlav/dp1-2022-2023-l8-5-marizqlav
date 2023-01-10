package org.springframework.samples.idus_martii.jugador;

import javax.persistence.Entity;

import javax.persistence.ManyToOne;

import javax.persistence.Table;



import org.springframework.samples.idus_martii.model.BaseEntity;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Peticion extends BaseEntity{
	
	@ManyToOne
	private Jugador solicita;
	@ManyToOne
	private Jugador solicitado;
	
}
