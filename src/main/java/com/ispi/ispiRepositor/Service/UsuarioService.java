/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.ExceptionMessages.BiUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.SenhaObrigatoriaNovoUsuario;
import com.ispi.projectoIspi.ExceptionMessages.EmailUsuarioExistenteException;
import com.ispi.projectoIspi.Repository.UsuarioRepository;
import com.ispi.projectoIspi.model.Usuario;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();

    public List<Usuario> getAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Usuario getOne(Long codigo) {
        return usuarioRepository.findById(codigo).orElse(null);

    }

    @Transactional
    public void addNew(Usuario usuario) {
        Optional<Usuario> emailExistente = usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());
        Optional<Usuario> biExistente = usuarioRepository.findByBiIgnoreCase(usuario.getBi());
        if (emailExistente.isPresent()) {
            throw new EmailUsuarioExistenteException("Este Email já se encontra registado no ISPI Payment!");
        }
        if (biExistente.isPresent()) {
            throw new BiUsuarioExistenteException("Este B.I já se encontra registado no ISPI Payment!");
        }
        if(usuario.isNovo() && usuario.getSenha()==""){
            throw new SenhaObrigatoriaNovoUsuario("A senha é obrigatória para novo usuário!");
            
        }
        if(usuario.isNovo()){
            usuario.setSenha(this.bCryptPasswordEncoder.encode(usuario.getSenha()));
            usuario.setConfirmacaoSenha(usuario.getSenha());
        }
        usuarioRepository.save(usuario);
    }

   /* public void update(Usuario usuario) {
        usuarioRepository.save(usuario);

    }

    public void delete(Long codigo) {
        usuarioRepository.deleteById(codigo);

    }*/

   /* public Usuario findByBiAndSituacao(String bi, Situacao situacao) {
        Usuario aluno = alunoRepository.findByBiAndSituacao(bi, situacao.INSCRITO);
        return aluno;
    }*/

    /* public Optional<Aluno> findByEmailIgnoreCase(String email) {
        Optional<Aluno> alunoOptional = alunoRepository.findByEmailIgnoreCase(email);
        if (alunoOptional.isPresent()) {
            throw new EmailUsuarioExistenteException("Este Email já se encontra registado!");
        }
        return alunoOptional;
    }*/
}
