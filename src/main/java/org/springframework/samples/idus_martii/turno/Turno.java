package org.springframework.samples.idus_martii.turno;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
	@Column(name = "turno_partida")
	private Integer numTurno;
    
	@ManyToOne
    private Jugador consul;

	@ManyToOne
    private Jugador predor;

	@ManyToOne
	private Jugador edil1;

	@ManyToOne
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
	
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private EstadoTurno estadoTurno;
	
}