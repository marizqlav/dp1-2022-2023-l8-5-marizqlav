package org.springframework.samples.idus_martii.partida;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {
    
    private PartidaRepository partidaRepo;

    @Autowired
    public PartidaService(PartidaRepository partidaRepo) {
        this.partidaRepo = partidaRepo;
    }

    
    public Partida findPartida(Integer id) {
        return partidaRepo.findById(id).get();
    }
    
    List<Partida> getPartidas(){
        return partidaRepo.findAll();
    }
    public void IniciarPartida(Integer id) {
        Partida partida = findPartida(id);

        Ronda rondaInicial = new Ronda();
        partida.getRondas().add(rondaInicial);
        
        Turno turnoInicial = new Turno();
        rondaInicial.getTurnos().add(turnoInicial);

        Integer randomNumberPlayer =  (int) (Math.random() * (partida.getNJugadores()));
        

    }
}