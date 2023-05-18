package br.com.jairfreitas.msavaliadorcredito.application.exception;

public class DadosClienteNotFoundException extends Exception {

    public DadosClienteNotFoundException() {
        super("Dados do cliente nao encontrado para o CPF informado.");
    }
}
