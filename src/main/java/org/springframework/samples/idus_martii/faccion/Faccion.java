package org.springframework.samples.idus_martii.faccion;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.springframework.samples.idus_martii.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Faccion extends BaseEntity{
	
	private FaccionesEnumerado faccionesposibles;  //Facciones a elegir solo 2
	
	@NotNull
	private FaccionesEnumerado faccionselecionada;
	
	
	//@OnetoMany					//Esperando clase jugador 
	//private String jugador;
}
