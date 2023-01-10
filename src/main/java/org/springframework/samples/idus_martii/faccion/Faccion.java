package org.springframework.samples.idus_martii.faccion;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.model.BaseEntity;
import org.springframework.samples.idus_martii.partida.Partida;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Faccion extends BaseEntity{
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private FaccionesEnumerado faccionPosible1;

	@NotNull
	@Enumerated(EnumType.STRING)
	private FaccionesEnumerado faccionPosible2;
	
	@Enumerated(EnumType.STRING)
	private FaccionesEnumerado faccionSelecionada = null;

	@JoinColumn(name = "partida_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    Partida partida;

	@ManyToOne(cascade = CascadeType.PERSIST, optional=false)
	@JoinColumn(name = "jugador_id")
	private Jugador jugador;
	
}
