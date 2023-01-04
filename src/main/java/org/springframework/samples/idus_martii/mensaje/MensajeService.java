package org.springframework.samples.idus_martii.mensaje;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MensajeService {

	
    private MensajeRepository mensajeRepository;

    @Autowired
    public MensajeService(MensajeRepository mensajeRepository){
        this.mensajeRepository=mensajeRepository;
    }
    @Transactional
    List<Mensaje> getMensajes(){
        return mensajeRepository.findAll();
    }
    @Transactional
    public Mensaje getById(int id){
        return mensajeRepository.findById(id).get();
    }
    @Transactional
    public List<Mensaje> getMensajesByPartidaId(int partidaid){
        return mensajeRepository.findMensajesByPartidaId(partidaid);
    }
    
    @Transactional(readOnly=true)
    public void deleteMensajeById(int id){
    	mensajeRepository.deleteById(id);
    }
    @Transactional(readOnly=true)
    public void save(Mensaje mensaje){
    	mensajeRepository.save(mensaje);
    }
}