package com.utnmdp.primerparcial.features.anuncios.excepciones;

public class LimiteOverbudgetSuperadoException extends RuntimeException {
    public LimiteOverbudgetSuperadoException(String message) {
        super(message);
    }
}
