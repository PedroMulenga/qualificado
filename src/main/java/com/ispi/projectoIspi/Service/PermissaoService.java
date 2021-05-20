/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Repository.PermissaoRepository;
import com.ispi.projectoIspi.model.Permissao;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public List<Permissao> getAll() {
        return (List<Permissao>) permissaoRepository.findAll();
    }

    public Optional<Permissao> getOne(Long codigo) {
        return permissaoRepository.findById(codigo);

    }

    public void addNew(Permissao permissao) {
        Optional<Permissao> permissaoOptional = permissaoRepository.findByNomeIgnoreCase(permissao.getNome());
        if(permissaoOptional.isPresent()){
            throw new NomeExistenteException("Nome já cadastrado!");
        }
        permissaoRepository.save(permissao);
    }

    public void update(Permissao permissao) {
        permissaoRepository.save(permissao);

    }
    public void delete(Long codigo){
        permissaoRepository.deleteById(codigo);
        
    }
}
