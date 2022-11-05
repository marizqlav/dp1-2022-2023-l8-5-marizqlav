package org.springframework.idus_martii.partida;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.idus_martii.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "facciones")
public class Faccion extends BaseEntity {
    //Esto esta asignado a Mario y Pablo, aunque yo (Ale) sigo sin entender los atributos de esta entidad. Revisar.
}
