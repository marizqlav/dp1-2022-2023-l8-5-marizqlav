package org.springframework.samples.idus_martii.partida.Exceptions;

public class CancelException extends Exception {
    public CancelException(String errorMessage) {
        super(errorMessage);
    }
}