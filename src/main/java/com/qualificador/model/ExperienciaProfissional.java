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
public class ExperienciaProfissional extends GenericDomin {

    @Column(nullable = false)
    @NotBlank(message = "O campo último local de trabalho é obrigatório!")
    private String localTrabalho;
    @Column(nullable = false)
    @NotBlank(message = "O campo funcao é obrigatório!")
    private String funcao;
    @Column(nullable = false)
    @NotBlank(message = "O campo funcao é obrigatório!")
    private String areaDeAtuacao;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O campo Título Académico é obrigatório!")
    private TituloAcademico tituloAcademico;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getAreaDeAtuacao() {
        return areaDeAtuacao;
    }

    public void setAreaDeAtuacao(String areaDeAtuacao) {
        this.areaDeAtuacao = areaDeAtuacao;
    }

    public TituloAcademico getTituloAcademico() {
        return tituloAcademico;
    }

    public void setTituloAcademico(TituloAcademico tituloAcademico) {
        this.tituloAcademico = tituloAcademico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
