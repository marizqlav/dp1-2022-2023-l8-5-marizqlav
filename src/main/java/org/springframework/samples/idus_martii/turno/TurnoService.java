package org.springframework.samples.idus_martii.turno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
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

    public void anadirVotoVerde(int turnoId, Jugador jugador) throws AccessException {
        Turno turno = getById(turnoId);

        if (jugador.equals(turno.getEdil1()) || jugador.equals(turno.getEdil2())) {
            throw new AccessException("Solo pueden votar los ediles");
        }

        turno.setVotosLeales(turno.getVotosLeales() + 1);
        save(turno);
    }

    public void anadirVotoRojo(int turnoId, Jugador jugador) throws AccessException {
        Turno turno = getById(turnoId);

        if (jugador.equals(turno.getEdil1()) || jugador.equals(turno.getEdil2())) {
            throw new AccessException("Solo pueden votar los ediles");
        }

        turno.setVotosTraidores(turno.getVotosTraidores() + 1);
        save(turno);
    }

    public void anadirVotoAmarillo(int turnoId, Jugador jugador) throws AccessException {
        Turno turno = getById(turnoId);

        if (jugador.equals(turno.getEdil1()) || jugador.equals(turno.getEdil2())) {
            throw new AccessException("Solo pueden votar los ediles");
        }

        turno.setVotosNeutrales(turno.getVotosNeutrales() + 1);
        save(turno);
    }

}