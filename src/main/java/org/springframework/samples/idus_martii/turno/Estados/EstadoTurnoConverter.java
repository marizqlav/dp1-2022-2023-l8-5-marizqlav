package org.springframework.samples.idus_martii.turno.Estados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EstadoTurnoConverter implements Converter<EstadoTurnoEnum, EstadoTurno> {

    EmpezarTurnoEstado empezarTurnoEstado;
    EstablecerRolesEstado establecerRolesEstado;
    VotarEstado votarEstado;
    EspiarEstado espiarEstado;
    CambiarVotoEstado cambiarVotoEstado;
    TerminarTurnoEstado terminarTurnoEstado;
    DescubiertoAmarilloEstado descubiertoAmarilloEstado;
    RecuentoEstado recuentoEstado;
    ElegirFaccion elegirFaccionEstado;
    
    @Autowired
    EstadoTurnoConverter(EstablecerRolesEstado establecerRolesEstado, VotarEstado votarEstado, EspiarEstado espiarEstado,
        CambiarVotoEstado cambiarVotoEstado, TerminarTurnoEstado terminarTurnoEstado, DescubiertoAmarilloEstado descubiertoAmarilloEstado,
        RecuentoEstado recuentoEstado, EmpezarTurnoEstado empezarTurnoEstado) {
            
        this.empezarTurnoEstado = empezarTurnoEstado;
        this.establecerRolesEstado = establecerRolesEstado;
        this.votarEstado = votarEstado;
        this.espiarEstado = espiarEstado;
        this.cambiarVotoEstado = cambiarVotoEstado;
        this.descubiertoAmarilloEstado = descubiertoAmarilloEstado;
        this.recuentoEstado = recuentoEstado;
        this.elegirFaccionEstado = elegirFaccionEstado;
        this.terminarTurnoEstado = terminarTurnoEstado;
    }

    @Override
    public EstadoTurno convert(EstadoTurnoEnum source) {
        switch (source) {
            case EmpezarTurno: {
                return empezarTurnoEstado;
            }
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
            case DescubiertoAmarillo: {
                return descubiertoAmarilloEstado;
            }
            case Recuento: {
                return recuentoEstado;
            }
            case ElegirFaccion: {
                return elegirFaccionEstado;
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
