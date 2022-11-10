package org.springframework.samples.idus_martii.faccion;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "faccion")
public class Faccion extends BaseEntity{
	@Enumerated(EnumType.STRING)
	private FaccionesEnumerado faccionposible1;
	@Enumerated(EnumType.STRING)
	private FaccionesEnumerado faccionposible2;//Facciones a elegir solo 2
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private FaccionesEnumerado faccionselecionada;

	
	//Esperando clase jugador
	@ManyToOne(optional=false)
	@JoinColumn(name = "jugador_id")
	private Jugador jugador;
	
}
