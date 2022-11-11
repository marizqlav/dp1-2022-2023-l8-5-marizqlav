package org.springframework.samples.idus_martii.partida;

import java.time.Duration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

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
	@Min(1)
	@Max(3)
	private Integer faccionGanadora;

    @Min(5)
    @Max(8)
    private Integer numeroJugadores;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    public String getDuration() {
        return Duration.between(fechaInicio, fechaFin).toString().substring(2).replace("M", " minutos ").replace("S", " segundos ");
    }
    
    public TipoFaccion getActualFaccionGanadora() {
    	TipoFaccion r = TipoFaccion.Leal;
    	if(this.faccionGanadora==1)
    		r = TipoFaccion.Leal;
    	if(this.faccionGanadora==2)
    		r = TipoFaccion.Traidor;
    	if(this.faccionGanadora==3)
    		r = TipoFaccion.Mercader;
    	return r;
    }

    //TODO relacion con Jugador

    //TODO Esta requiere de faccion
    /*@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "partida")
    Set<Faccion> faccionesJugadoras; //TODO aplicar restriccion de tres facciones en service*/

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "partida")
    List<Ronda> rondas;
    
    @OneToMany(cascade = CascadeType.PERSIST) //TODO mappedBy
    List<Mensaje> mensajes;

    //TODO Requiere entidad jugador
    @ManyToMany
    @JoinTable(
        name = "partida_jugador", 
        joinColumns = @JoinColumn(name = "partida_id"), 
        inverseJoinColumns = @JoinColumn(name = "jugador_id"))
    Set<Jugador> espectadores;
}