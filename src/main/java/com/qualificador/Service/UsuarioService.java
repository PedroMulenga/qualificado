/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Service;

import com.qualificador.ExceptionMessages.DataInvalidaException;
import com.qualificador.ExceptionMessages.EmailUsuarioExistenteException;
import com.qualificador.ExceptionMessages.SenhaObrigatoriaNovoUsuario;
import com.qualificador.ExceptionMessages.TelefoneUsuarioExistenteException;
import com.qualificador.Repository.UsuarioRepository;
import com.qualificador.model.Usuario;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public List<Usuario> getAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Page<Usuario> findAll(int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return usuarioRepository.findAll(pageable);
    }

    public List<Usuario> findAllQuadros() {
        return (List<Usuario>) usuarioRepository.findAllQuadros();
    }

    public Optional<Usuario> getOne(Long codigo) {
        return usuarioRepository.findById(codigo);

    }

    @Transactional
    public void addNew(Usuario usuario) {

        // CÁLCULO DA IDADE DO ESTUDANTE UTILIZANDO LOCALDATE
        LocalDate anoSistema = LocalDate.now();
        int anoConvertido = anoSistema.getYear();
        int idadeCalculada = anoConvertido - usuario.getDataNascimento().getYear();
        Optional<Usuario> emailExistente = usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());
        Optional<Usuario> telefoneExistente = usuarioRepository.findByTelefone(usuario.getTelefone());
        if (emailExistente.isPresent() && !emailExistente.get().equals(usuario)) {
            throw new EmailUsuarioExistenteException("Este Email já se encontra registado!");
        }
        if (telefoneExistente.isPresent() && !telefoneExistente.get().equals(usuario)) {
            throw new TelefoneUsuarioExistenteException("Este Telefone já se encontra registado!");
        }
        if (usuario.getCodigo() == null && StringUtils.isEmpty(usuario.getSenha())) {
            throw new SenhaObrigatoriaNovoUsuario("A senha é obrigatória para novo usuário!");

        }
        if (idadeCalculada <= 17) {
            throw new DataInvalidaException("Sua idade não é permitda");
        }
        if (usuario.getCodigo() == null || !StringUtils.isEmpty(usuario.getSenha())) {
            usuario.setSenha(this.bCryptPasswordEncoder.encode(usuario.getSenha()));
        } else if (StringUtils.isEmpty(usuario.getSenha())) {
            usuario.setSenha(emailExistente.get().getSenha());
        }
        if (usuario.getCodigo() == null && StringUtils.isEmpty(usuario.getNomeImagen())) {
            usuario.setNomeImagen("userDefault.png");
        }
        if (usuario.getCodigo() != null && StringUtils.isEmpty(usuario.getNomeImagen())) {
            usuario.setNomeImagen(emailExistente.get().getNomeImagen());
        }
        usuario.setConfirmacaoSenha(usuario.getSenha());
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByEmailIgnoreCase(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email);
    }

    public void delete(Long codigo) {
        usuarioRepository.deleteById(codigo);

    }

    public List<Usuario> finByEstadoIsTrue() {
        return usuarioRepository.findByEstadoIsTrue();
    }

    public List<Usuario> finByProfissao(String profissao) {
        return usuarioRepository.findByProfissao(profissao);
    }

    public void updateResetPasswordToken(String token, String email) {
        Usuario emailUsuario = usuarioRepository.findByEmail(email);
        if (emailUsuario != null) {
            emailUsuario.setResetPasswordToken(token);
            usuarioRepository.save(emailUsuario);

        } else {
            throw new EmailUsuarioExistenteException("Não existe usuário com E-mail " + email);
        }

    }

    public Usuario get(String resetPasswordToken) {
        return usuarioRepository.findByResetPasswordToken(resetPasswordToken);

    }

    public void updatePassword(Usuario usuario, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCriptografada = passwordEncoder.encode(newPassword);
        usuario.setSenha(senhaCriptografada);
        usuario.setResetPasswordToken(null);
        usuarioRepository.save(usuario);
    }

}
