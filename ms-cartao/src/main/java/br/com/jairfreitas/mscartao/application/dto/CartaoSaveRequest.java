package br.com.jairfreitas.mscartao.application.dto;

import br.com.jairfreitas.mscartao.domain.Cartao;
import br.com.jairfreitas.mscartao.domain.enums.BandeiraCartao;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public Cartao toModel(){
        return new Cartao(nome, bandeira, renda, limiteBasico);
    }
}
