package br.com.jairfreitas.msavaliadorcredito.service;

import br.com.jairfreitas.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import br.com.jairfreitas.msavaliadorcredito.application.exception.ErroComunicacaoMicroserviceException;
import br.com.jairfreitas.msavaliadorcredito.domain.model.*;
import br.com.jairfreitas.msavaliadorcredito.infra.clients.CartoesResourceClient;
import br.com.jairfreitas.msavaliadorcredito.infra.clients.ClienteResourceClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliadorCreditoService {
    @Autowired
    private ClienteResourceClient resourceClient;
    @Autowired
    private CartoesResourceClient cartoesResourceClient;
    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException {
        try {
            ResponseEntity<DadosCliente> dadosCliente = resourceClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> dadosCartao = cartoesResourceClient.getCartoesByClientes(cpf);
            return SituacaoCliente
                    .builder()
                    .cliente(dadosCliente.getBody())
                    .cartoes(dadosCartao.getBody())
                    .build();

        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);
        }
    }
    
    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = resourceClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> dadosCartoesResponse = cartoesResourceClient.getCartoesPorRenda(renda);
            
            List<Cartao> cartaoList = dadosCartoesResponse.getBody();
            var listaCartoesAprovados = cartaoList.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();
                        
                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeDB = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeDB.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);
                
                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);
                
                return aprovado;
            }).collect(Collectors.toList());
            
            return new RetornoAvaliacaoCliente(listaCartoesAprovados);
            
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);
        }
    }
}
