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
import org.springframework.stereotype.Service;
import com.qualificador.Repository.CertificacaoRepository;
import com.qualificador.Repository.ExperienciaProfissionalRepository;
import com.qualificador.model.Certificacao;
import com.qualificador.model.ExperienciaProfissional;
import com.qualificador.model.Usuario;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class ExperienciaProfissionalService {

    @Autowired
    private ExperienciaProfissionalRepository experienciaProfissionalRepository;

    public List<ExperienciaProfissional> getAll() {
        return (List<ExperienciaProfissional>) experienciaProfissionalRepository.findAll();
    }

    public List<ExperienciaProfissional> findByUsuario(Usuario usuario) {
        return (List<ExperienciaProfissional>) experienciaProfissionalRepository.findByUsuario(usuario);
    }

    public Optional<ExperienciaProfissional> getOne(Long codigo) {
        return experienciaProfissionalRepository.findById(codigo);

    }

    public void addNew(ExperienciaProfissional experienciaProfissional) {
        experienciaProfissionalRepository.save(experienciaProfissional);
    }

    public void update(ExperienciaProfissional experienciaProfissional) {
        experienciaProfissionalRepository.save(experienciaProfissional);

    }

    public void delete(Long codigo) {
        experienciaProfissionalRepository.deleteById(codigo);

    }

}
