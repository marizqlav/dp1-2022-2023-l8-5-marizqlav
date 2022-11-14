package org.springframework.samples.idus_martii.partida;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.idus_martii.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sufragium")
public class Sufragium extends BaseEntity{
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private Partida partida;
	
	@Column(name = "leales")
	int leales;
	
	@Column(name = "traidores")
	int traidores;
	
	@Column(name = "max")
	int max;
	
}
