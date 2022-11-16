package org.springframework.samples.idus_martii.turno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.stereotype.Service;

@Service
public class TurnoService {

    TurnoRepository repo;

    @Autowired
    public TurnoService(TurnoRepository repo){
        this.repo=repo;
    }

    List<Turno> getTurnos(){
        return repo.findAll();
    }
    
    public Turno getById(int id){
        return repo.findById(id).get();
    }

    public void deleteTurnoById(int id){
        repo.deleteById(id);
    }

    public void save(Turno turno){
        repo.save(turno);
    }

    public void continuarTurno(Integer turnoId) {
        Turno turno = getById(turnoId);

        switch (turno.getEstadoTurno()) {
            case Principio_turno: {
                turno.setEstadoTurno(EstadoTurno.Elegir_rol);
                repo.save(turno);
                break;
            }
            case Elegir_rol: {
                turno.setEstadoTurno(EstadoTurno.Esperar_voto);
                repo.save(turno);
                break;
            }
            case Esperar_voto: {
                turno.setEstadoTurno(EstadoTurno.Cambiar_voto);
                repo.save(turno);
                break;
            }
            case Cambiar_voto: {
                turno.setEstadoTurno(EstadoTurno.Votar_de_nuevo);
                repo.save(turno);
                break;
            }
            case Votar_de_nuevo: {
                turno.setEstadoTurno(EstadoTurno.Contar_votos);
                repo.save(turno);
                break;
            }
            case Contar_votos: {
                turno.setEstadoTurno(EstadoTurno.Elegir_faccion);
                repo.save(turno);
                break;
            }
            case Elegir_faccion: {
                turno.setEstadoTurno(EstadoTurno.Terminar_turno);
                repo.save(turno);
                break;
            }
            case Terminar_turno:
                break;
            default:
                break;
  
        }
    }

    public void anadirVotoVerde(int turnoId, Jugador jugador) throws AccessException {
        Turno turno = getById(turnoId);

        if (!(jugador.equals(turno.getEdil1()) || jugador.equals(turno.getEdil2()))) {
            throw new AccessException("Solo pueden votar los ediles");
        }

        turno.setVotosLeales(turno.getVotosLeales() + 1);
        save(turno);
    }

    //TODO restriccion un jugador solo puede votar una vez
    public void anadirVotoRojo(Integer turnoId, Jugador jugador) throws AccessException {
        Turno turno = getById(turnoId);
        System.out.println("hola");
        if (!(jugador.equals(turno.getEdil1()) || jugador.equals(turno.getEdil2()))) {
            throw new AccessException("Solo pueden votar los ediles");
        }

        turno.setVotosTraidores(turno.getVotosTraidores() + 1);
        save(turno);
    }

    public void anadirVotoAmarillo(Integer turnoId, Jugador jugador) throws AccessException {
        Turno turno = getById(turnoId);

        if (!(jugador.equals(turno.getEdil1()) || jugador.equals(turno.getEdil2()))) {
            throw new AccessException("Solo pueden votar los ediles");
        }

        turno.setVotosNeutrales(turno.getVotosNeutrales() + 1);
        save(turno);
    }
    
    public FaccionesEnumerado espiarVotos(int turnoId, int jugadorId){
    	return repo.espiarVoto(turnoId, jugadorId);
    }
    
    public void cambiarVoto(int turnoId, int jugadorId, FaccionesEnumerado voto){
    	repo.findVotoByturnoAndPlayer(turnoId, jugadorId).setTipoVoto(voto);
    }

}