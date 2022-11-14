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
import org.springframework.samples.idus_martii.partida.Partida;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "faccion")
public class Faccion extends BaseEntity{
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private FaccionesEnumerado faccionPosible1;

	@NotNull
	@Enumerated(EnumType.STRING)
	private FaccionesEnumerado faccionPosible2; //Facciones a elegir solo 2
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private FaccionesEnumerado faccionSelecionada;

	
	@JoinColumn(name = "partida_id")
    @ManyToOne(cascade = CascadeType.REMOVE)
    Partida partida;

	@ManyToOne(optional=false)
	@JoinColumn(name = "jugador_id")
	private Jugador jugador;
	
}
