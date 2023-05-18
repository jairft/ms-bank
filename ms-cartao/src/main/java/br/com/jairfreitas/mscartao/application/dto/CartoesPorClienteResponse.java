package br.com.jairfreitas.mscartao.application.dto;

import br.com.jairfreitas.mscartao.domain.CartaoCliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteResponse {

    private String nome;
    private String badeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorClienteResponse fromModel(CartaoCliente model){
        return new CartoesPorClienteResponse(model.getCartao().getNome(), model.getCartao().getBandeira().toString(),
                model.getLimite());
    }
}
