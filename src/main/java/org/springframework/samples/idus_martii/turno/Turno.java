package org.springframework.samples.idus_martii.turno;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.samples.idus_martii.model.BaseEntity;
import org.springframework.samples.idus_martii.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Turno extends BaseEntity {
	@NotNull
	@NotEmpty
    private String consul;
	@NotNull
	@NotEmpty
    private String predor;
	@NotNull
	@NotEmpty
    private String edil1;
	@NotNull
	@NotEmpty
    private String edil2;
	@Size(min = 0, max = 2)
    private Integer votosTraidores;
	@Size(min = 0, max = 2)
    private Integer votosLeales;
	@Size(min = 0, max = 2)
    private Integer votosNeutrales;
}