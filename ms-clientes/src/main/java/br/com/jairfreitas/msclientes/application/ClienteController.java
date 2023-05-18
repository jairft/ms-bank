package br.com.jairfreitas.msclientes.application;

import br.com.jairfreitas.msclientes.domain.Cliente;
import br.com.jairfreitas.msclientes.representation.ClienteSaveRequest;
import br.com.jairfreitas.msclientes.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@Slf4j
public class ClienteController {
    @Autowired
    private ClienteService service;
    @GetMapping
    public String status(){
        log.info("Obtendo o status do microservice de clientes");
        return "OK";
    }
    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody ClienteSaveRequest request){
        var cliente = request.toModel();
        service.salvarCliente(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }
    @GetMapping(params = "cpf")
    public ResponseEntity<?> dadosCliente(@RequestParam("cpf") String cpf){
        var cliente = service.getByCpf(cpf);
        if (cliente.isPresent()){
            return ResponseEntity.ok().body(cliente);
        }
        return ResponseEntity.notFound().build();
    }
}
