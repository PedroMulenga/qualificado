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
import com.qualificador.model.Certificacao;
import com.qualificador.model.Usuario;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class CertificacaoService {

    @Autowired
    private CertificacaoRepository certificacaoRepository;

    public List<Certificacao> getAll() {
        return (List<Certificacao>) certificacaoRepository.findAll();
    }

    public List<Certificacao> findByUsuario(Usuario usuario) {
        return (List<Certificacao>) certificacaoRepository.findByUsuario(usuario);
    }

    public Optional<Certificacao> getOne(Long codigo) {
        return certificacaoRepository.findById(codigo);

    }

    public void addNew(Certificacao certificacao) {
        certificacaoRepository.save(certificacao);
    }

    public void update(Certificacao certificacao) {
        certificacaoRepository.save(certificacao);

    }

    public void delete(Long codigo) {
        certificacaoRepository.deleteById(codigo);

    }

}
