package org.springframework.idus_martii.turno;

import javax.persistence.Entity;

import org.springframework.idus_martii.model.BaseEntity;

@Entity
public class Turno extends BaseEntity{
    //TODO restringir numero de turnos al numero de jugadores de la partida
}
