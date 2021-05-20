/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author tecno-base
 */
@SuppressWarnings("serial")
@Entity
public class Bairro extends GenericDomin {

    @Column(length = 50, nullable = false)
    @NotBlank(message = "O campo nome é obrigatório!")
    private String nome;
    @Column(length = 50, nullable = false)
    @NotBlank(message = "O campo nome vulgar é obrigatório!")
    private String vulgar;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVulgar() {
        return vulgar;
    }

    public void setVulgar(String vulgar) {
        this.vulgar = vulgar;
    }
}
