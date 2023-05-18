package br.com.jairfreitas.msavaliadorcredito.infra.clients;

import br.com.jairfreitas.msavaliadorcredito.domain.model.Cartao;
import br.com.jairfreitas.msavaliadorcredito.domain.model.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "ms-cartao", path = "/cartoes")
public interface CartoesResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByClientes(@RequestParam("cpf") String cpf);
    @GetMapping(params = "renda")
    ResponseEntity<List<Cartao>> getCartoesPorRenda(@RequestParam("renda") Long renda);
}
