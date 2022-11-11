package org.springframework.samples.idus_martii.ronda;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
	Set<Turno> turnos = new HashSet<>(); //Por alguna razón el set no se inicializa solo
}