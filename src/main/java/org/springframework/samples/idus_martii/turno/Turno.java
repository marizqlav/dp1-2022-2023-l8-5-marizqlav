package org.springframework.samples.idus_martii.turno;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.model.BaseEntity;
import org.springframework.samples.idus_martii.ronda.Ronda;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "turno")
public class Turno extends BaseEntity {
	@NotNull
	@ManyToOne
    private Jugador consul;

	@NotNull
	@ManyToOne
    private Jugador predor;

	@NotNull
	@ManyToOne
	private Jugador edil1;

	@NotNull
	@ManyToOne
    private Jugador edil2;

	@Min(0)
	@Max(2)
    private Integer votosTraidores;

	@Min(0)
	@Max(2)
    private Integer votosLeales;

	@Min(0)
	@Max(2)
    private Integer votosNeutrales;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ronda_id")
	private Ronda ronda;
}