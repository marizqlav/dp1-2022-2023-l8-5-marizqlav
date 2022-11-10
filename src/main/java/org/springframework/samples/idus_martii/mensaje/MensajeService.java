package org.springframework.samples.idus_martii.mensaje;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeService {

    private MensajeRepository mensajeRepository;

    @Autowired
    public MensajeService(MensajeRepository mensajeRepository){
        this.mensajeRepository=mensajeRepository;
    }

    List<Mensaje> getMensajes(){
        return mensajeRepository.findAll();
    }
    
    public Mensaje getById(int id){
        return mensajeRepository.findById(id).get();
    }

    public void deleteMensajeById(int id){
    	mensajeRepository.deleteById(id);
    }

    public void save(Mensaje mensaje){
    	mensajeRepository.save(mensaje);
    }
}