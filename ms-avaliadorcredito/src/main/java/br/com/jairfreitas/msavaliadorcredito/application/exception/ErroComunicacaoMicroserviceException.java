package br.com.jairfreitas.msavaliadorcredito.application.exception;

import lombok.Getter;

public class ErroComunicacaoMicroserviceException extends Exception {

    @Getter
    private Integer status;
    public ErroComunicacaoMicroserviceException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
