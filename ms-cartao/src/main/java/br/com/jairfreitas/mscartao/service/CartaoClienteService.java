package br.com.jairfreitas.mscartao.service;

import br.com.jairfreitas.mscartao.domain.CartaoCliente;
import br.com.jairfreitas.mscartao.infra.repository.CartaoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoClienteService {
    @Autowired
    private CartaoClienteRepository repository;

    public List<CartaoCliente> listCartoesByCpf(String cpf){
        return repository.findByCpf(cpf);
    }
}
