package com.leilao.backend.service;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.leilao.backend.exception.NaoEncontradoExcecao;
import com.leilao.backend.model.Perfil;
import com.leilao.backend.model.Pessoa;
import com.leilao.backend.repository.PerfilRepository;
import com.leilao.backend.repository.PessoaRepository;

@Service
public class PerfilService {
    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    public Perfil inserir(Perfil perfil) {
        Perfil perfilCadastrado = perfilRepository.save(perfil);
        // emailService.enviarEmailSimples(pessoaCadastrada.getEmail(), "Cadastrado com
        // Sucesso", "Cadastro no Sistema de Leilão XXX foi feito com sucesso!");
        enviarEmailSucesso(perfilCadastrado);
        return perfilCadastrado;
    }

    private void enviarEmailSucesso(Perfil perfil) {
        Context context = new Context();
        context.setVariable("nome", perfil.getNome());
        emailService.emailTemplate(perfil.getEmail(), "Cadastro Sucesso", context, "cadastroSucesso");
    }

    public Perfil alterar(Perfil perfil) {
        // return pessoaRepository.save(pessoa);
        Perfil perfilBanco = buscarPorId(perfil.getId());
        perfilBanco.setNome(perfil.getNome());
        perfilBanco.setEmail(perfil.getEmail());
        return perfilRepository.save(perfilBanco);
    }

    public void excluir(Long id) {
        Perfil perfilBanco = buscarPorId(id);
        perfilRepository.delete(perfilBanco);
    }

    public Perfil buscarPorId(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoExcecao(messageSource.getMessage("perfil.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Perfil> buscarTodos(Pageable pageable) {
        return perfilRepository.findAll(pageable);
    }
}
