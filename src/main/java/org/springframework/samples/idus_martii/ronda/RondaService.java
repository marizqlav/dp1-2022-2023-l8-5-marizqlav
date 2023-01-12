package org.springframework.samples.idus_martii.ronda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RondaService {

    RondaRepository repo;

    @Autowired
    public RondaService(RondaRepository repo){
        this.repo=repo;
    }
    @Transactional
    List<Ronda> getRondas(){
        return repo.findAll();
    }
    @Transactional
    public Ronda getById(int id){
    	Ronda r=null;
		try{
			r= this.repo.findById(id).get();
		}catch(Exception e) {
		}
		return r;
    }
    @Transactional
    public void deleteRondaById(int id){
        repo.deleteById(id);
    }
    @Transactional
    public void save(Ronda ronda){
        repo.save(ronda);
    }
}