package org.springframework.samples.idus_martii.turno;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.expression.AccessException;
import org.springframework.samples.idus_martii.faccion.FaccionesConverter;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.Partida;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.samples.idus_martii.turno.Estados.EstadoTurno;
import org.springframework.samples.idus_martii.turno.VotosTurno;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TurnoService {

    TurnoRepository repo;
    PartidaService partidaService;
    FaccionesConverter faccionesConverter;

    @Autowired
    public TurnoService(TurnoRepository repo, @Lazy PartidaService partidaService, FaccionesConverter faccionesConverter){
        this.repo = repo;
        this.partidaService = partidaService;
        this.faccionesConverter = faccionesConverter;
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

    //TODO restriccion un jugador solo puede votar una vez
    public void anadirVoto(Integer partidaId, Jugador jugador, String strVoto) throws AccessException {
        Turno turno = partidaService.getTurnoActual(partidaId);
        
        if (!(jugador.equals(turno.getEdil1()) || jugador.equals(turno.getEdil2()))) {
            throw new AccessException("Solo pueden votar los ediles");
        }

        FaccionesEnumerado voto = faccionesConverter.convert(strVoto);
        if (voto == FaccionesEnumerado.Traidor) {
            turno.setVotosTraidores(turno.getVotosTraidores() + 1);
            anadirVotoTurno(turno.getId(), jugador.getId(), "Negativo");
            save(turno);
        } else
        if (voto == FaccionesEnumerado.Leal) {
            turno.setVotosLeales(turno.getVotosLeales() + 1);
            anadirVotoTurno(turno.getId(), jugador.getId(), "Positivo");
            save(turno);    
        } else
        if (voto == FaccionesEnumerado.Mercader) {
            turno.setVotosNeutrales(turno.getVotosNeutrales() + 1);
            save(turno);    
        }
    }

    public void cambiarVoto(Integer turnoId, Integer jugadorId, String voto){
    	repo.findVotoByturnoAndPlayer(turnoId, jugadorId).setTipoVoto(voto);
    }
    
    public VotosTurno findVoto(Integer turnoId, Integer jugadorId){
    	return repo.findVotoByturnoAndPlayer(turnoId, jugadorId);
    }
    
    public void anadirVotoTurno(Integer turnoId, Integer jugadorId, String voto){
    	repo.anadirVotoTurno(turnoId, jugadorId,voto);
    }

    //TODO esto debería reducirse a un solo espiar con enumerados o algo
    public void espiarVoto(Integer partidaId, Jugador jugador, String voto) throws AccessException {
        Turno turno = partidaService.getTurnoActual(partidaId);

        if (jugador != turno.getPredor()) {
            throw new AccessException("Esta partida no ha sido iniciada");
        }
        
        if (voto == "1") {
            VotosTurno votoEdil1 = findVoto(turno.getId(), turno.getEdil1().getId());
            votoEdil1.setEspiado(true);
        } else
        if (voto == "2") {
            VotosTurno votoEdil2 = findVoto(turno.getId(), turno.getEdil2().getId());
            votoEdil2.setEspiado(true);
        }
    }
    
    /*public void asignarRol(String rol, Jugador jugador, Integer turnoId) { //TODO revisar
    	
    	Turno turno = getById(turnoId);
    	int anterior = repo.findById(turnoId).get().getNumTurno()-1;
    	
    	
    	List<Jugador> edilesTurnoAnterior= new ArrayList<>();
    	edilesTurnoAnterior.add(repo.turnoPorNumero(anterior).getEdil1());
    	edilesTurnoAnterior.add(repo.turnoPorNumero(anterior).getEdil2());
    	
    	List<Jugador> edilesTurnoActual= new ArrayList<>();
    	if(repo.turnoPorNumero(anterior+1).getEdil1()!=null) {edilesTurnoActual.add(jugador);}
    	if(repo.turnoPorNumero(anterior+1).getEdil2()!=null) {edilesTurnoActual.add(jugador);}
    	
    	
    	
    	
    	if(rol.equals("predor") && repo.turnoPorNumero(anterior).getPredor() != jugador) {
    		turno.setPredor(jugador);
    	}
    	else if(!edilesTurnoAnterior.contains(jugador) 
    			&& (edilesTurnoActual.get(0)==null || (edilesTurnoActual.get(1)==null)) ) {
        			edilesTurnoActual.add(jugador);
    	}
    	else if(edilesTurnoAnterior.contains(jugador) && turno.getRonda().getPartida().getNumeroJugadores()==5
    			&& (edilesTurnoActual.isEmpty() || !edilesTurnoActual.contains(jugador))) {
    			edilesTurnoActual.add(jugador);
    	}
    	else {
    		System.out.println("Ha petao el edil, mal hecho");
    	}
    	
    	turno.setEdil1(edilesTurnoActual.get(0));
    	turno.setEdil1(edilesTurnoActual.get(1));
    	save(turno);
    }*/

}