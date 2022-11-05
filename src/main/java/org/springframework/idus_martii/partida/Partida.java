package org.springframework.idus_martii.partida;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.idus_martii.jugador.Jugador;
import org.springframework.idus_martii.mensaje.Mensaje;
import org.springframework.idus_martii.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "partidas")
public class Partida extends BaseEntity {

    TipoFaccion faccionGanadora;

    @Size(min = 5, max = 8)
    Integer nJugadores;

    LocalDate fechaCreacion;

    LocalDate fechaInicio;

    LocalDate fechaFin;

    Duration getDuration() {
        return Duration.between(fechaInicio, fechaFin);
    }

    //Esta requiere de faccion
    /*@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "partida")
    Set<Faccion> faccionesJugadoras; //TODO aplicar restriccion de tres facciones en service*/

    @OneToMany(cascade = CascadeType.PERSIST)
    List<Ronda> rondas;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    List<Mensaje> mensajes;

    @ManyToMany
    @JoinTable(
        name = "partida_jugador", 
        joinColumns = @JoinColumn(name = "partida_id"), 
        inverseJoinColumns = @JoinColumn(name = "jugador_id"))
    Set<Jugador> espectadores;
}
