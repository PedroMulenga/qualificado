/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Repository;

import com.qualificador.model.Certificacao;
import com.qualificador.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PEDRO P MULENGA
 */
@Repository
public interface CertificacaoRepository extends JpaRepository<Certificacao, Long> {

    List<Certificacao> findByUsuario(Usuario usuario);

}
