package br.com.jairfreitas.mscartao.application;

import br.com.jairfreitas.mscartao.application.dto.CartaoSaveRequest;
import br.com.jairfreitas.mscartao.application.dto.CartoesPorClienteResponse;
import br.com.jairfreitas.mscartao.domain.Cartao;
import br.com.jairfreitas.mscartao.domain.CartaoCliente;
import br.com.jairfreitas.mscartao.service.CartaoClienteService;
import br.com.jairfreitas.mscartao.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;
    @Autowired
    private CartaoClienteService clienteService;
    @GetMapping
    public String status(){
        return "OK";
    }
    @PostMapping
    public ResponseEntity<Cartao> cadastrar(@RequestBody CartaoSaveRequest request){
        Cartao cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesPorRenda(@RequestParam("renda") Long renda){
       List<Cartao> cartaoList = cartaoService.getCartoesMenorIgual(renda);
       return ResponseEntity.ok().body(cartaoList);
    }
    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByClientes(@RequestParam("cpf") String cpf){
       List<CartaoCliente> clienteList =  clienteService.listCartoesByCpf(cpf);
       List<CartoesPorClienteResponse> responseList = clienteList.stream()
               .map(CartoesPorClienteResponse:: fromModel)
               .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseList);
    }

}
