package br.com.jairfreitas.mscartao.infra.repository;

import br.com.jairfreitas.mscartao.domain.CartaoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoClienteRepository extends JpaRepository<CartaoCliente, Long> {

    List<CartaoCliente> findByCpf(String cpf);
}
