package org.springframework.samples.idus_martii.partida;

import java.time.Duration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
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


    @Min(5)
    @Max(8)
    private Integer numeroJugadores;

    
	@Enumerated(EnumType.STRING)
    FaccionesEnumerado faccionGanadora;


    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    public String getDuration() {
    	if(this.fechaFin==null)
    		return "No finalizada";
    	else
    		return Duration.between(fechaInicio, fechaFin).toString().substring(2).replace("M", " minutos ").replace("S", " segundos ");
    }
    
    public String getFechaCreacionParseada() {
    	if(this.fechaCreacion==null)
    		return "No creada";
    	else
    		return this.fechaCreacion.toString().replace("T", " ").replace("-", "/").substring(0,this.fechaCreacion.toString().length()-7);
    }
    
    public String getFechaInicioParseada() {
    	if(this.fechaInicio==null)
    		return "No iniciada";
    	else
    		return this.fechaInicio.toString().replace("T", " ").replace("-", "/").substring(0,this.fechaCreacion.toString().length()-7);
    }
    
    public String getFechaFinParseada() {
    	if(this.fechaFin==null)
    		return "No finalizada";
    	else
    		return this.fechaFin.toString().replace("T", " ").replace("-", "/").substring(0,this.fechaCreacion.toString().length()-7);
    }
    public String getEstadoPartida() {
    	if(this.fechaInicio==null)
    		return "Esperando jugadores";
    	else
    		if(this.fechaInicio!=null && this.fechaFin==null)
    			return "En juego";
    		else
    			return "Finalizada";
    }
    

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "partida")
    Set<Faccion> faccionesJugadoras;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "partida")
    List<Ronda> rondas = new ArrayList();
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "partida") //TODO mappedBy
    List<Mensaje> mensajes;

    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "jugador_id")
    Jugador jugador;
    
    @ManyToMany
    @JoinTable(
        name = "partida_jugador", 
        joinColumns = @JoinColumn(name = "partida_id"), 
        inverseJoinColumns = @JoinColumn(name = "jugador_id"))
    Set<Jugador> espectadores;
    
    
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "partida")
    Lobby lobby;
    
}