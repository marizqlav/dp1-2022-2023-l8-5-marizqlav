package org.springframework.samples.idus_martii.ronda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RondaService {

    RondaRepository repo;

    @Autowired
    public RondaService(RondaRepository repo){
        this.repo=repo;
    }

    List<Ronda> getRondas(){
        return repo.findAll();
    }
    
    public Ronda getById(int id){
        return repo.findById(id).get();
    }

    public void deleteRondaById(int id){
        repo.deleteById(id);
    }

    public void save(Ronda ronda){
        repo.save(ronda);
    }
    
     public Integer anadirRonda(int numRonda, int idpartida) {
		return repo.anadirRonda(numRonda,idpartida);
	}
}