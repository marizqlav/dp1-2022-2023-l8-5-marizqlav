package org.springframework.samples.idus_martii.turno.Estados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.idus_martii.faccion.FaccionesEnumerado;
import org.springframework.samples.idus_martii.partida.GameScreens.EspiarScreen;
import org.springframework.samples.idus_martii.partida.GameScreens.GameScreen;
import org.springframework.samples.idus_martii.turno.Turno;
import org.springframework.samples.idus_martii.turno.TurnoService;
import org.springframework.samples.idus_martii.turno.VotosTurno;
import org.springframework.stereotype.Component;

@Component
public class EspiarEstado implements EstadoTurno {

    private EspiarScreen espiarScreen;

    private TurnoService turnoService;

    @Autowired
    EspiarEstado(EspiarScreen espiarScreen, @Lazy TurnoService turnoService) {
        this.espiarScreen = espiarScreen;

        this.turnoService = turnoService;
    }

    @Override
    public void takeAction(Turno context) {

    }

    @Override
    public EstadoTurnoEnum getNextState(Turno context) {
        VotosTurno votoEdil1 = turnoService.findVoto(context.getId(), context.getEdil1().getId());
        VotosTurno votoEdil2 = turnoService.findVoto(context.getId(), context.getEdil2().getId());

        if (votoEdil1.getEspiado()) {
            if (votoEdil1.getTipoVoto() == FaccionesEnumerado.Mercader) {
                return EstadoTurnoEnum.DescubiertoAmarillo;
            }
            return EstadoTurnoEnum.CambiarVoto;
        } else
        if (votoEdil2.getEspiado()) {
            if (votoEdil2.getTipoVoto() == FaccionesEnumerado.Mercader) {
                return EstadoTurnoEnum.DescubiertoAmarillo;
            }
            return EstadoTurnoEnum.CambiarVoto;
        }
        return EstadoTurnoEnum.Espiar;
    }

    @Override
    public GameScreen getGameScreen() {
        return espiarScreen;
    }
    
}
