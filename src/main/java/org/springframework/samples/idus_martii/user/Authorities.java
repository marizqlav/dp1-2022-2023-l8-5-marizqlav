package org.springframework.samples.idus_martii.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.validation.constraints.Size;

import org.springframework.samples.idus_martii.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Authorities extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name = "username")
	User user;
	
	@Size(min = 3, max = 50)
	String authority;
	
	
}
