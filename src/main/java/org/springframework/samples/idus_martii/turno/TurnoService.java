package org.springframework.samples.idus_martii.turno;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.expression.AccessException;
import org.springframework.samples.idus_martii.faccion.FaccionesConverter;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.partida.PartidaService;
import org.springframework.stereotype.Service;

@Service
public class TurnoService {

    TurnoRepository repo;
    VotosTurnoRepository repoVotosTurno;
    PartidaService partidaService;
    FaccionesConverter faccionesConverter;

    @Autowired
    public TurnoService(TurnoRepository repo, @Lazy PartidaService partidaService, FaccionesConverter faccionesConverter, VotosTurnoRepository repoVotosTurno){
        this.repo = repo;
        this.partidaService = partidaService;
        this.faccionesConverter = faccionesConverter;
        this.repoVotosTurno = repoVotosTurno;
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
    public void anadirVoto(Integer turnoId, Jugador jugador, String strVoto) throws AccessException {
        Turno turno = repo.findById(turnoId).get();
        
        if (!(jugador.equals(turno.getEdil1()) || jugador.equals(turno.getEdil2()))) {
            throw new AccessException("Solo pueden votar los ediles");
        }

        anadirVotoTurno(turno, jugador, faccionesConverter.convert(strVoto));

        save(turno);
    }

    public void cambiarVoto(Integer turnoId, Jugador jugador, Integer edilId, String voto) throws AccessException {
        Turno turno = repo.findById(turnoId).get();
        VotosTurno v = findVoto(turnoId, edilId);

        if (!(jugador.equals(turno.getPredor()) || 
            (findVoto(turnoId, jugador.getId()) != null && findVoto(turnoId, jugador.getId()).getTipoVoto() == FaccionesEnumerado.Mercader))) {

            throw new AccessException("Solo pueden cambiar los votos los Predores o los Ediles con voto amarillo");
        }

        if (!v.getEspiado()) {
            throw new AccessException("Solo se pueden cambiar votos espiados");
        }

        if (v.getVotoOriginal() != null) {
            throw new AccessException("No se puede cambiar un voto ya cambiado");
        }

        v.setVotoOriginal(v.getTipoVoto());

        v.setTipoVoto(faccionesConverter.convert(voto));

        repoVotosTurno.save(v);
    }
    
    public VotosTurno findVoto(Integer turnoId, Integer jugadorId){
    	return repoVotosTurno.findVotoByturnoAndPlayer(turnoId, jugadorId);
    }
    
    public void anadirVotoTurno(Turno turno, Jugador jugador, FaccionesEnumerado voto) {
        VotosTurno votosTurno = new VotosTurno();
        votosTurno.turno = turno;
        votosTurno.jugador = jugador;
        votosTurno.tipoVoto = voto;
    	repoVotosTurno.save(votosTurno);
    }

    public void espiarVoto(Integer partidaId, Jugador jugador, String voto) throws AccessException {
        Turno turno = partidaService.getTurnoActual(partidaId);

        if (jugador != turno.getPredor()) {
            throw new AccessException("Solo los Predores pueden espiar");
        }

        VotosTurno votoEdil1 = findVoto(turno.getId(), turno.getEdil1().getId());
        VotosTurno votoEdil2 = findVoto(turno.getId(), turno.getEdil2().getId());
        
        if (votoEdil1.getEspiado() || votoEdil2.getEspiado()) {
            throw new AccessException("Solo puedes espiar una vez por turno");
        }

        if (voto.equals("1")) {
            votoEdil1.setEspiado(true);
            repoVotosTurno.save(votoEdil1);
        } else
        if (voto.equals("2")) {
            votoEdil2.setEspiado(true);
            repoVotosTurno.save(votoEdil2);
        }
    }

    public List<Jugador> getJugadoresValidosParaRol(Integer turnoId, String rol) {

        List<Jugador> jugadoresValidos = new ArrayList<>();

    	Turno turno = getById(turnoId);
        
        for (Jugador j : partidaService.findJugadores(turno.getRonda().getPartida().getId())) {
            jugadoresValidos.add(j);
        }
        
        if (turno.getConsul() != null) { jugadoresValidos.remove(turno.getConsul()); }
        if (turno.getPredor() != null) { jugadoresValidos.remove(turno.getPredor()); }
        if (turno.getEdil1() != null) { jugadoresValidos.remove(turno.getEdil1()); }
        if (turno.getEdil2() != null) { jugadoresValidos.remove(turno.getEdil2()); }
        
        if (rol.equals("edil") && turno.getRonda().getPartida().getNumeroJugadores() != 5) {
            
            Turno turnoAnterior = turno.getRonda().getTurnos().get(turno.getRonda().getTurnos().size() - 2);

            if (jugadoresValidos.contains(turnoAnterior.getEdil1())) { jugadoresValidos.remove(turnoAnterior.getEdil1()); }
            if (jugadoresValidos.contains(turnoAnterior.getEdil2())) { jugadoresValidos.remove(turnoAnterior.getEdil2()); }
        }

        return jugadoresValidos;
    }
    
    public void asignarRol(Integer turnoId, Jugador jugador) throws InvalidPlayerException {
    	
        Turno turno = getById(turnoId);

        String rol = "";
        if (turno.getPredor() == null) {
            rol = "predor";
        } else
        if (turno.getEdil1() == null || turno.getEdil2() == null) {
            rol = "edil";
        }

        if (!getJugadoresValidosParaRol(turnoId, rol).contains(jugador)) {
            throw new InvalidPlayerException("Este jugador no es valido");
        }

        if (rol.equals("predor")) {
            turno.setPredor(jugador);
        }
        if (rol.equals("edil")) {
            if (turno.getEdil1() == null) {
                turno.setEdil1(jugador);
            } else
            if (turno.getEdil2() == null) {
                turno.setEdil2(jugador);
            }
        }

        save(turno);
    }

}