package org.springframework.samples.idus_martii.turnos;

import javax.persistence.Entity;

import org.springframework.samples.idus_martii.model.BaseEntity;
import org.springframework.samples.idus_martii.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Turno extends BaseEntity {
    private String consul;
    private String predor;
    private String edil1;
    private String edil2;
    private Integer votosTraidores;
    private Integer votosLeales;
    private Integer votosNeutrales;
}