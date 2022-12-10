package org.springframework.samples.idus_martii.faccion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class FaccionesConverter implements Converter<String, FaccionesEnumerado> {

    @Override
    @Nullable
    public FaccionesEnumerado convert(String source) {
        if (source.equals("leal") || source.equals("verde") || source.equals("Leal") || source.equals("Verde")) {
            return FaccionesEnumerado.Leal;
        } else
        if (source.equals("traidor") || source.equals("rojo") || source.equals("Traidor") || source.equals("Rojo")) {
            return FaccionesEnumerado.Traidor;
        } else
        if (source.equals("mercader") || source.equals("amarillo") || source.equals("Mercader") || source.equals("Amarillo")) {
            return FaccionesEnumerado.Mercader;
        } else {
            return null;
        }
    }
    
}
