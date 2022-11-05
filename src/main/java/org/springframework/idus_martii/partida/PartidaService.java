package org.springframework.idus_martii.partida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {
    
    private PartidaRepository partidaRepo;

    private RondaRepository rondaRepo;

    @Autowired
    public PartidaService(PartidaRepository partidaRepo, RondaRepository rondaRepo) {
        this.partidaRepo = partidaRepo;
        this.rondaRepo = rondaRepo;
    }
    
}
