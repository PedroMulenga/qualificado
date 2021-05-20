/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.ExceptionMessages.EmailUsuarioExistenteException;
import com.ispi.projectoIspi.Enum.Situacao;
import com.ispi.projectoIspi.ExceptionMessages.BiUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.TelefoneUsuarioExistenteException;
import com.ispi.projectoIspi.Repository.AlunoRepository;
import com.ispi.projectoIspi.model.Aluno;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> getAll() {
        return (List<Aluno>) alunoRepository.findAll();
    }

    public Page<Aluno> findAll(int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return alunoRepository.findAll(pageable);
    }

    public Optional<Aluno> getOne(Long codigo) {
        return alunoRepository.findById(codigo);

    }

    public void addNew(Aluno aluno) {
        Optional<Aluno> emailOptional = alunoRepository.findByEmailIgnoreCase(aluno.getEmail());
        Optional<Aluno> telefoneOptional = alunoRepository.findByTelefone(aluno.getTelefone());
        Optional<Aluno> biOptional = alunoRepository.findByBi(aluno.getBi().toUpperCase());
        if (emailOptional.isPresent()) {
            throw new EmailUsuarioExistenteException("Este Email já se encontra registado!");
        }
        if (telefoneOptional.isPresent()) {
            throw new TelefoneUsuarioExistenteException("Este Telefone já se encontra registado!");
        }
        if (biOptional.isPresent()) {
            throw new BiUsuarioExistenteException("Este B.I já se encontra registado!");
        }
        alunoRepository.save(aluno);
    }

    public void update(Aluno aluno) {
        alunoRepository.save(aluno);

    }

    public void delete(Long codigo) {
        alunoRepository.deleteById(codigo);

    }

    public Aluno findByBiAndSituacao(String bi, Situacao situacao) {
        Aluno aluno = alunoRepository.findByBiAndSituacao(bi, situacao.INSCRITO);
        return aluno;
    }
}
