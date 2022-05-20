/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Repository;



import com.ispi.projectoIspi.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PEDRO P MULENGA
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    public Optional<Usuario> findByEmailIgnoreCase(String email);
    public Optional<Usuario> findByEmailIgnoreCaseAndEstadoIsTrue(String email);
    public Optional<Usuario> findByBiIgnoreCase(String bi);
    @Query("SELECT DISTINCT p.nome FROM Usuario u JOIN u.grupos g JOIN g.permissoes p WHERE u= :usuario")
    public List<String> findByPermissoes(@Param("usuario") Usuario usuario);
    
}
