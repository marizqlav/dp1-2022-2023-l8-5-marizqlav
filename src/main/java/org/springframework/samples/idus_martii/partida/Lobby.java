package org.springframework.samples.idus_martii.partida;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	 private List<Jugador> jugadores;

    @OneToOne
	private Partida partida;
    
   
}