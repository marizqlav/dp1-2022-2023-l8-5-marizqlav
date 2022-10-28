package org.springframework.samples.idus_martii.turnos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}