package org.springframework.samples.idus_martii.jugador;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.mensaje.Mensaje;
import org.springframework.samples.idus_martii.model.BaseEntity;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.statistics.Achievement;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name ="peticiones")
public class Peticion extends BaseEntity{
	
	@ManyToOne
	private Jugador solicita;
	@ManyToOne
	private Jugador solicitado;
	
}
