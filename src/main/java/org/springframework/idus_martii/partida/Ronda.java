package org.springframework.idus_martii.partida;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.idus_martii.model.BaseEntity;
import org.springframework.idus_martii.turno.Turno;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rondas")
public class Ronda extends BaseEntity {
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "partida_id")
    Partida partida; //TODO aplicar restriccion de dos rondas en service

    //Requiere de turno
    /*@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "rondas")
    List<Turno> turnos;*/
}
