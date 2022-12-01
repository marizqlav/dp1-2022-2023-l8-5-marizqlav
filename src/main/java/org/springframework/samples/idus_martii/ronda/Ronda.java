package org.springframework.samples.idus_martii.ronda;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.samples.idus_martii.model.BaseEntity;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.samples.idus_martii.turno.Turno;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ronda")
public class Ronda extends BaseEntity {
	@NotNull
	@JoinColumn(name = "partida_id")
	@ManyToOne(cascade = CascadeType.REMOVE)
    Partida partida;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "ronda")
	List<Turno> turnos = new ArrayList();
	
	public Integer getNumRonda() {
		for (int i = 0; i < partida.getRondas().size(); i++) {
			if (partida.getRondas().get(i).getId() == this.getId()) {
				return i + 1;
			}
		}
		return null;
	}
}