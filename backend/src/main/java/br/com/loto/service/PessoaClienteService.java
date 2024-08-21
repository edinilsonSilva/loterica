package br.com.loto.service;

import br.com.loto.dto.PessoaClienteRequestDTO;
import br.com.loto.entity.Account;
import br.com.loto.repository.AccountClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Lazy)
public class PessoaClienteService {

    private final AccountClientRepository pessoaRepository;

    private final AccountPermissionServiceImpl accountPermissionServiceImpl;

    public Account registrar(PessoaClienteRequestDTO pessoaClienteRequestDTO) {
        Account account = new PessoaClienteRequestDTO().converter(pessoaClienteRequestDTO);
        Account objetoNovo = pessoaRepository.saveAndFlush(account);
        accountPermissionServiceImpl.vincularPessoaPermissaoCliente(objetoNovo);
        return objetoNovo;
    }
}
