package org.springframework.samples.idus_martii.ronda;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.idus_martii.model.BaseEntity;
import org.springframework.samples.idus_martii.pet.Pet;
import org.springframework.samples.idus_martii.turno.Turno;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ronda")
public class Ronda extends BaseEntity {
	@NotNull
	
    private Integer partidaId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ronda")
	private Set<Turno> turnos;

}