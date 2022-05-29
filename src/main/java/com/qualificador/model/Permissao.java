/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author justino
 */
@SuppressWarnings("serial")
@Entity
public class Permissao extends GenericDomin {

    @Column(length = 50, nullable = false)
    @NotNull(message = "O campo nome é obrigatório!")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
