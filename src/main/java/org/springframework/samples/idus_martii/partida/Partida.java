package org.springframework.samples.idus_martii.partida;

import java.time.Duration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
//import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.mensaje.Mensaje;
import org.springframework.samples.idus_martii.model.BaseEntity;
import org.springframework.samples.idus_martii.ronda.Ronda;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "partida")
public class Partida extends BaseEntity {
    
	@Enumerated(EnumType.STRING)
    FaccionesEnumerado faccionGanadora;

    @Size(min = 5, max = 8)
    Integer nJugadores;

    LocalDateTime fechaCreacion;

    LocalDateTime fechaInicio;

    LocalDateTime fechaFin;

    Duration getDuration() {
        return Duration.between(fechaInicio, fechaFin);
    }

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "partida")
    Set<Faccion> faccionesJugadoras;
    //TODO aplicar restriccion de tres facciones en service

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "partida")
    List<Ronda> rondas;
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "partida") //TODO mappedBy
    List<Mensaje> mensajes;

    @ManyToMany
    @JoinTable(
        name = "partida_jugador", 
        joinColumns = @JoinColumn(name = "partida_id"), 
        inverseJoinColumns = @JoinColumn(name = "jugador_id"))
    Set<Jugador> espectadores;
}