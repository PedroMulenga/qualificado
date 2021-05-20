/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author justino
 */
@SuppressWarnings("serial")
@Entity
public class Grupo extends GenericDomin {

    @Column(length = 50, nullable = false)
    @NotBlank(message = "O campo nome é obrigatório!")
    private String nome;
    @Transient
    private boolean estadoBolean = true;
    @ManyToMany
    @JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name = "codigo_grupo"),
             inverseJoinColumns = @JoinColumn(name = "codigo_permissao"))
    private List<Permissao> permissoes;

    private String estado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        estado = "Inactivo";
        if (estadoBolean == true) {
            estado = "Activo";
        }
        return estado;
    }

    public boolean isEstadoBolean() {
        return estadoBolean;
    }

    public void setEstadoBolean(boolean estadoBolean) {
        this.estadoBolean = estadoBolean;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

}
