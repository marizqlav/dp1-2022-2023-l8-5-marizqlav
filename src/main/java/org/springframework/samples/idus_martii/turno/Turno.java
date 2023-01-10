package org.springframework.samples.idus_martii.turno;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.model.BaseEntity;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.turno.Estados.EstadoTurnoEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Turno extends BaseEntity {
	    
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
    private Jugador consul;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
    private Jugador predor;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Jugador edil1;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
    private Jugador edil2;

	@Min(0)
	@Max(2)
    private Integer votosTraidores = 0;

	@Min(0)
	@Max(2)
    private Integer votosLeales = 0;

	@Min(0)
	@Max(2)
    private Integer votosNeutrales = 0;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ronda_id")
	private Ronda ronda;
	
	private EstadoTurnoEnum estadoTurno = EstadoTurnoEnum.EmpezarTurno;

	public Integer getNumTurno() {
		for (int i = 0; i < ronda.getTurnos().size(); i++) {
			if (ronda.getTurnos().get(i).getId() == this.getId()) {
				return i + 1;
			}
		}
		return null;
	}
	
}