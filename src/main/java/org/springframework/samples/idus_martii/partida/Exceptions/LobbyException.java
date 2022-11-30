package org.springframework.samples.idus_martii.partida.Exceptions;

public class LobbyException extends Exception {
    public LobbyException(String errorMessage) {
        super(errorMessage);
    }
}
