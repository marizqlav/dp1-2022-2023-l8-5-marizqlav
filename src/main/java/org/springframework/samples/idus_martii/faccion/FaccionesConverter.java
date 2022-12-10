package org.springframework.samples.idus_martii.faccion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class FaccionesConverter implements Converter<String, FaccionesEnumerado> {

    @Override
    @Nullable
    public FaccionesEnumerado convert(String source) {
        if (source == "leal" || source == "verde" || source == "Leal" || source == "Verde") {
            return FaccionesEnumerado.Leal;
        } else
        if (source == "traidor" || source == "rojo" || source == "Traidor" || source == "Rojo") {
            return FaccionesEnumerado.Traidor;
        } else
        if (source == "mercader" || source == "amarillo" || source == "Mercader" || source == "Amarillo") {
            return FaccionesEnumerado.Mercader;
        } else {
            return null;
        }
    }
    
}
