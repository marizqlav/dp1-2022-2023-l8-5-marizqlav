package org.springframework.samples.idus_martii.faccion;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.idus_martii.model.BaseEntity;
import org.springframework.samples.idus_martii.player.Player;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "faccion")
public class Faccion extends BaseEntity{
	
	private List<FaccionesEnumerado> faccionesposibles;  //Facciones a elegir solo 2
	
	@NotNull
	private FaccionesEnumerado faccionselecionada;

	
	//Esperando clase jugador
	@ManyToOne(optional=false)
	@JoinColumn(name = "player_id")
	private Player player;
	
}
