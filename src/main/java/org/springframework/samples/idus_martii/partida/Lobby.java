package org.springframework.samples.idus_martii.partida;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.idus_martii.jugador.Jugador;

//import org.springframework.samples.idus_martii.jugador.Jugador;

import org.springframework.samples.idus_martii.model.BaseEntity;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "lobby")
public class Lobby extends BaseEntity {

	@ManyToMany
	private List<Jugador> jugadores = new ArrayList();

    @OneToOne(cascade = CascadeType.REMOVE)
	private Partida partida;
    
}