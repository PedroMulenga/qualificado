/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.model;

import com.qualificador.Enum.TituloAcademico;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author justino
 */
@SuppressWarnings("serial")
@Entity
public class Certificacao extends GenericDomin {

    @Column(nullable = false)
    @NotBlank(message = "O nome da certificação é obrigatório!")
    private String nomeCertificacao;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    public String getNomeCertificacao() {
        return nomeCertificacao;
    }

    public void setNomeCertificacao(String nomeCertificacao) {
        this.nomeCertificacao = nomeCertificacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    

}
