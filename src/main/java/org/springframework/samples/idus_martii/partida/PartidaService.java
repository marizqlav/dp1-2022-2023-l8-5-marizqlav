package org.springframework.samples.idus_martii.partida;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idus_martii.faccion.Faccion;
import org.springframework.samples.idus_martii.faccion.FaccionService;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.jugador.Jugador;
import org.springframework.samples.idus_martii.ronda.Ronda;
import org.springframework.samples.idus_martii.ronda.RondaService;
import org.springframework.samples.idus_martii.turno.EstadoTurno;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {
    
    private PartidaRepository partidaRepo;

    private RondaService rondaService;
    private TurnoService turnoService;
    private FaccionService faccionService;

    @Autowired
    public PartidaService(PartidaRepository partidaRepo, TurnoService turnoService, RondaService rondaService, 
        FaccionService faccionService) {
        this.partidaRepo = partidaRepo;
        this.turnoService = turnoService;
        this.rondaService = rondaService;
        this.faccionService = faccionService;
    }

    public void save(Partida partida) {
        partidaRepo.save(partida);
    }
    
    public void cancelarPartida(int id){
    	partidaRepo.deleteById(id);
    }
    
    public Partida findPartida(Integer id) {
        return partidaRepo.findById(id).get();
    }

    public List<Jugador> findJugadores(Integer partidaId) {
        return partidaRepo.findJugadores(partidaId);
    }
    
    public void IniciarPartida(Integer id, Lobby lobby) throws InitiationException {

        Partida partida = findPartida(id);

        //Restricciones
        if (partida.getFechaInicio() != null) {
            throw new InitiationException("No se puede iniciar una partida ya iniciada");
        } else if (lobby.getJugadores().size() > 8) {
            throw new InitiationException("Demasiados jugadores");
        } else if (lobby.getJugadores().size() < 5) {
            throw new InitiationException("No hay suficientes jugadores");
        }

    	partida.setFechaInicio(LocalDateTime.now());

        List<Jugador> jugadores = lobby.getJugadores();

        Ronda rondaInicial = new Ronda();
        rondaInicial.setNumRonda(1);
        rondaInicial.setPartida(partida);
        partida.getRondas().add(rondaInicial);
       
        Turno turnoInicial = new Turno();
        turnoInicial.setEstadoTurno(EstadoTurno.Elegir_rol);
        turnoInicial.setNumTurno(1);
        turnoInicial.setRonda(rondaInicial);
        rondaInicial.getTurnos().add(turnoInicial);
        
        Function<Integer, Integer> addNumber = x -> (x >= jugadores.size()) ? 0 : x + 1;

        Integer n = (int) Math.floor((Math.random() * (partida.getNumeroJugadores())));

        turnoInicial.setConsul(jugadores.get(n));
        addNumber.apply(n);

        turnoInicial.setPredor(jugadores.get(n));
        addNumber.apply(n);

        turnoInicial.setEdil1(jugadores.get(n));
        addNumber.apply(n);

        turnoInicial.setEdil2(jugadores.get(n));
        System.out.println(n);
        
        rondaService.save(rondaInicial);
        turnoService.save(turnoInicial);

        List<FaccionesEnumerado> faccionesBag = new ArrayList<>();
        for (int i = 0; i < 2; i++) { faccionesBag.add(FaccionesEnumerado.Mercader); }
        for (int i = 0; i < jugadores.size() - 1; i++) { faccionesBag.add(FaccionesEnumerado.Leal); }
        for (int i = 0; i < jugadores.size() - 1; i++) { faccionesBag.add(FaccionesEnumerado.Traidor); }

        for (Jugador jugador : jugadores) {

            Faccion faccion = new Faccion();
            faccion.setJugador(jugador);
            faccion.setPartida(partida);
            faccion.setFaccionSelecionada(null);
            
            Integer r1 = (int) Math.floor(Math.random() * faccionesBag.size());
            FaccionesEnumerado f1 = faccionesBag.get(r1);
            faccionesBag.remove(f1);
            faccion.setFaccionPosible1(f1);

            Integer r2 = (int) Math.floor(Math.random() * faccionesBag.size());
            FaccionesEnumerado f2 = faccionesBag.get(r2);
            faccionesBag.remove(f2);
            faccion.setFaccionPosible2(f2);

            faccionService.save(faccion);
        }

        save(partida);
    }
    
public void roles(Integer turnoPartida, Integer partidaId) {
    	
    	List<Jugador> listaJugadores = partidaRepo.findJugadores(partidaId);
        Function<Integer, Integer> addNumber = x -> (x == listaJugadores.size()) ? 0 : x + 1;
        
        Turno turno = new Turno();
        turno.setNumTurno(turnoPartida);
        Integer posicionConsul = listaJugadores.indexOf(turno.getConsul());
        Integer n =  posicionConsul+1;
        turno.setConsul(listaJugadores.get(n));
        addNumber.apply(n);

        turno.setPredor(listaJugadores.get(n));
        addNumber.apply(n);

        turno.setEdil1(listaJugadores.get(n));
        addNumber.apply(n);

        turno.setEdil2(listaJugadores.get(n));
        addNumber.apply(n);
        
        turnoService.save(turno);

    }
    
////Final ronda 1 y ronda 2
//  public void GetRondaTerminar(Partida partida, Turno turnoEnPartida, Ronda ronda) {    	
//  	
//      if(turnoEnPartida.getNumTurno()+1 >= (partida.getNumeroJugadores())*2){
//          if(partida.votosLeal(turnoEnPartida)-partida.votosTraidores(turnoEnPartida) >= 2){
//          	partida.setFaccionGanadora(FaccionesEnumerado.Leal);
//          }else if(partida.votosTraidores(turnoEnPartida)-partida.votosLeal(turnoEnPartida) >= 2){
//          	partida.setFaccionGanadora(FaccionesEnumerado.Traidor);
//          }else{
//              partida.setFaccionGanadora(FaccionesEnumerado.Mercader);
//          }
//
////          finalizarPartida(partida, turno);
//
//      }else  if(turnoEnPartida.getNumTurno()+1 >= partida.getNumeroJugadores() && ronda.getNumRonda() == 2){
//          Jugador consul = partida.getRondas().get(1).getTurnos().stream()
//              .filter(x-> x.getNumTurno()==(turnoEnPartida.getNumTurno()+1)%(partida.getNumeroJugadores()))
//              .map(x->x.getConsul()).collect(Collectors.toList()).get(0);
//          if(consul == turnoEnPartida.getConsul()) {
//          	turnoService.save(turnoEnPartida);
//          }else {
//          	ModelAndView resultConsul=new ModelAndView("redirect:/");
//          	resultConsul.addObject("message", "Se ha cancelado la partida");
//              return resultConsul;
//          }
//      }else{
//          //Primera ronda
//      	Jugador consul = partida.getRondas().get(0).getTurnos().stream()
//      			.map(x->x.getConsul()).collect(Collectors.toList()).get(0);
////      	Turno nuevoTurno = partida.makeTurn(new Turno(partida, turnoEnPartida.getTurnoPartida()+1), 8);
////          turnoService.save(nuevoTurno);
//      }
//  	ModelAndView result=new ModelAndView("/partidas/tablero");
//      result.addObject("partida", partidaService.findPartida(partida.getId()));
//      return result;
//
//  }

    List<Partida> getPartidas() {
		return partidaRepo.findAll();
	}
    
    List<Partida> getPartidasEnJuego() {
		return partidaRepo.findAllEnJuego();
	}

    Partida getPartidaIniciada(int idpartida) {
		return partidaRepo.findPartidaIniciada(idpartida);
	}

    List<Partida> getPartidasFinalizadasJugador(int idjugador) {
		return partidaRepo.findAllFinalizadasJugador(idjugador);
	}

    Lobby getLobby(int idpartida) {
		return partidaRepo.getLobby(idpartida);
	}
    
    Integer anadirLobby(int idlobby, int idpartida) {
		return partidaRepo.anadirLobby(idlobby, idpartida);
	}
    
    Integer anadirJugadorLobby(int idjugador, int idlobby) {
		return partidaRepo.anadirJugadorLobby(idjugador, idlobby);
	}
    
    Jugador findJugadorInLobby(int idjugador, int idlobby) {
		return partidaRepo.findJugadorInLobby(idjugador, idlobby);
	}
    
    Partida jugadorPartidaEnCurso(int idjugador) {
		return partidaRepo.jugadorPartidaEnCurso(idjugador);
	}

    Ronda rondaActual(Integer partidaId) {
    	Partida p = partidaRepo.findById(partidaId).get();
    	Ronda r = p.getRondas().get(p.getRondas().size()-1);
    	return r;
    }

    Turno turnoActual(Integer partidaId) {
    	Partida p = partidaRepo.findById(partidaId).get();
    	Ronda r = p.getRondas().get(p.getRondas().size()-1);
    	Turno t = r.getTurnos().get(r.getTurnos().size()-1);
    	return t;
    }
    
    Ronda rondaActual(int partidaId) {
    	return partidaRepo.findById(partidaId).get().getRondas().get(partidaRepo.findById(partidaId).get().getRondas().size()-1);

    }
}