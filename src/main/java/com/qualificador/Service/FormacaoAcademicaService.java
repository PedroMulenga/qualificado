/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Service;

import com.qualificador.ExceptionMessages.NomeExistenteException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.qualificador.Repository.FormacaoAcademicaRepository;
import com.qualificador.model.FormacaoAcademica;
import com.qualificador.model.Usuario;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class FormacaoAcademicaService {

    @Autowired
    private FormacaoAcademicaRepository formacaoAcademicaRepository;

    public List<FormacaoAcademica> getAll() {
        return (List<FormacaoAcademica>) formacaoAcademicaRepository.findAll();
    }

    public List<FormacaoAcademica> findByUsuario(Usuario usuario) {
        return (List<FormacaoAcademica>) formacaoAcademicaRepository.findByUsuario(usuario);
    }

    public Page<FormacaoAcademica> findAll(int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return formacaoAcademicaRepository.findAll(pageable);
    }

    public Optional<FormacaoAcademica> getOne(Long codigo) {
        return formacaoAcademicaRepository.findById(codigo);

    }

    public void addNew(FormacaoAcademica formacaoAcademica) {
        Optional<FormacaoAcademica> formacaoAcademicaOptional = formacaoAcademicaRepository.findByNomeAndUsuario(formacaoAcademica.getNome(), formacaoAcademica.getUsuario());
        if (formacaoAcademicaOptional.isPresent() && !formacaoAcademicaOptional.get().equals(formacaoAcademica)) {
            throw new NomeExistenteException("O Nome já se encontra cadastrado!");
        }
        formacaoAcademicaRepository.save(formacaoAcademica);
    }

    public void update(FormacaoAcademica formacaoAcademica) {
        formacaoAcademicaRepository.save(formacaoAcademica);

    }

    public void delete(Long codigo) {
        formacaoAcademicaRepository.deleteById(codigo);

    }

}
