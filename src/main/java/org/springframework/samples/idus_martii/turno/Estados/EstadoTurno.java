package org.springframework.samples.idus_martii.turno.Estados;

import org.jpatterns.gof.StatePattern;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;

@StatePattern
@StatePattern.State
public interface EstadoTurno {

	public void takeAction(Turno context);

    public EstadoTurnoEnum getNextState(Turno context);

    public GameScreen getGameScreen();
}

// Principio_turno,
// Elegir_rol,
// Esperar_voto,
// Cambiar_voto,
// Votar_de_nuevo,
// Contar_votos,
// Elegir_faccion,
// Terminar_turno;
