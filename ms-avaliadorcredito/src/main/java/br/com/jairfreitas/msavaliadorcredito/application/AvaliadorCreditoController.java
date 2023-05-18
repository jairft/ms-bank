package br.com.jairfreitas.msavaliadorcredito.application;

import br.com.jairfreitas.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import br.com.jairfreitas.msavaliadorcredito.application.exception.ErroComunicacaoMicroserviceException;
import br.com.jairfreitas.msavaliadorcredito.domain.model.DadosAvaliacao;
import br.com.jairfreitas.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import br.com.jairfreitas.msavaliadorcredito.domain.model.SituacaoCliente;
import br.com.jairfreitas.msavaliadorcredito.service.AvaliadorCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "avaliador-credito")
public class AvaliadorCreditoController {
    @Autowired
    private AvaliadorCreditoService creditoService;
    @GetMapping
    public String status(){
        return "OK";
    }

    @GetMapping(path = "situacao-cliente", params = "cpf")
    public ResponseEntity<?> consultaSituacaoCliente(@RequestParam("cpf") String cpf)  {
        try {
            SituacaoCliente situacaoCliente = creditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok().body(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroserviceException e) {
            return (ResponseEntity) ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getStatus());
        }
    }
    @PostMapping
    public ResponseEntity<?> realizarAvaliacao(@RequestBody DadosAvaliacao dados){
        try {
          RetornoAvaliacaoCliente retornoAvaliacaoCliente = creditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
          return ResponseEntity.ok().body(retornoAvaliacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroserviceException e) {
            return (ResponseEntity) ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getStatus());
        }
    }
}
