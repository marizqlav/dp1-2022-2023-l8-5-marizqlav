package org.springframework.samples.idus_martii.turno;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "votos_turno")
public class VotosTurno extends BaseEntity{

	
	@ManyToOne(optional = false)
	Turno turno;
	
	@ManyToOne(optional = false)
	Jugador jugador;
	
	FaccionesEnumerado tipoVoto;
}
