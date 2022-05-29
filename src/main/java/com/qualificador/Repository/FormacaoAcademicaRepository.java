/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Repository;
import com.qualificador.model.FormacaoAcademica;
import com.qualificador.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PEDRO P MULENGA
 */
@Repository
public interface FormacaoAcademicaRepository extends JpaRepository<FormacaoAcademica, Long>{

    public Optional<FormacaoAcademica> findByNomeAndUsuario(String nome, Usuario usuario);
    public List<FormacaoAcademica> findByUsuario(Usuario usuario);


}
