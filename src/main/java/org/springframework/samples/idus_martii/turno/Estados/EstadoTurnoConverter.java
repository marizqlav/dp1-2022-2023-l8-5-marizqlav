package org.springframework.samples.idus_martii.turno.Estados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class EstadoTurnoConverter implements Converter<EstadoTurnoEnum, EstadoTurno> {

    EstablecerRolesEstado establecerRolesEstado;
    VotarEstado votarEstado;
    EspiarEstado espiarEstado;
    CambiarVotoEstado cambiarVotoEstado;
    TerminarTurnoEstado terminarTurnoEstado;
    
    @Autowired
    EstadoTurnoConverter(EstablecerRolesEstado establecerRolesEstado, VotarEstado votarEstado, EspiarEstado espiarEstado,
        CambiarVotoEstado cambiarVotoEstado, TerminarTurnoEstado terminarTurnoEstado) {
        this.establecerRolesEstado = establecerRolesEstado;
        this.votarEstado = votarEstado;
        this.espiarEstado = espiarEstado;
        this.cambiarVotoEstado = cambiarVotoEstado;
        this.terminarTurnoEstado = terminarTurnoEstado;
    }

    @Override
    public EstadoTurno convert(EstadoTurnoEnum source) {
        switch (source) {
            case EstablecerRoles: {
                return establecerRolesEstado;
            }
            case Votar: {
                return votarEstado;
            }
            case Espiar: {
                return espiarEstado;
            }
            case CambiarVoto: {
                return cambiarVotoEstado;
            }
            case TerminarTurno: {
                return terminarTurnoEstado;
            }
        
            default: {
                return null;
            }
        }
    }
    
}
