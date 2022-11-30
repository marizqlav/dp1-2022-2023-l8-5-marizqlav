package org.springframework.samples.idus_martii.turno.Estados;

import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;

public interface EstadoTurno {

	public void takeAction(Turno context);

    public EstadoTurno getNextState(Turno context); //Return null to keep the current state

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
