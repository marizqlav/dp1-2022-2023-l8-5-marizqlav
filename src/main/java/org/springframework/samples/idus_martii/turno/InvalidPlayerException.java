package org.springframework.samples.idus_martii.turno;

public class InvalidPlayerException extends Exception {
    public InvalidPlayerException(String errorMessage) {
        super(errorMessage);
    }
}
