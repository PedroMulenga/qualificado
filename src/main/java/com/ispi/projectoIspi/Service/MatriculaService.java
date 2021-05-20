/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import com.ispi.projectoIspi.ExceptionMessages.EntidadeUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.NumeroEstudanteUsuarioExistenteException;
import com.ispi.projectoIspi.Repository.MatriculaRepository;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Turma;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    public List<Matricula> getAll() {
        return (List<Matricula>) matriculaRepository.findAll();
    }

    public Optional<Matricula> getOne(Long codigo) {
        return matriculaRepository.findById(codigo);

    }

    public void addNew(Matricula matricula) {
        Optional<Matricula> numeroEstudante = matriculaRepository.findByNumeroEstudante(matricula.getNumeroEstudante());
        Optional<Matricula> alunoOptional = matriculaRepository.findByAluno(matricula.getAluno());
        if (numeroEstudante.isPresent()) {
            throw new NumeroEstudanteUsuarioExistenteException("Nº de Estudante já cadastrado, Gerar outro!");
        }
        if (alunoOptional.isPresent()) {
            throw new EntidadeUsuarioExistenteException("O Estudante já se encontra cadastrado!");
        }
        matriculaRepository.save(matricula);
    }

    public void update(Matricula matricula) {
        matriculaRepository.save(matricula);

    }

    public void delete(Long codigo) {
        matriculaRepository.deleteById(codigo);

    }

    public Iterable<Matricula> findByTurma(Turma turma) {
        return matriculaRepository.findByTurma(turma);
    }

    public Matricula findByNumeroEstudanteAndSituacao(String numeroEstudante, SituacaoMatricula situacao) {
        Matricula matricula = matriculaRepository.findByNumeroEstudanteAndSituacao(numeroEstudante, situacao.MATRICULADO);
        return matricula;
    }

    public Matricula findByBiEstudanteAndSituacao(String bi, String numeroEstudante) {
        Matricula matricula = matriculaRepository.findByBiEstudanteAndSituacao(bi, numeroEstudante);
        return matricula;
    }

    public Optional<Matricula> findByNumeroEstudante(String numeroEstudante) {
        Optional<Matricula> matricula = matriculaRepository.findByNumeroEstudante(numeroEstudante);
        return matricula;
    }

}
