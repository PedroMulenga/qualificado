/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import com.ispi.projectoIspi.Enum.AnoCurricular;
import com.ispi.projectoIspi.Enum.Periodo;
import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author PEDRO P MULENGA
 */
@SuppressWarnings("serial")
@Entity
public class Matricula extends GenericDomin {

    @Column(length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O campo Períudo é obrigatório!")
    private Periodo periodo;
    @Column(length = 15, nullable = false)
    @NotNull(message = "O campo Ano Académico é obrigatório!")
    private Integer anoAcademico;
    @Column(length = 10, nullable = false)
    @NotBlank(message = "O campo Nº de Estudante é obrigatório!")
    private String numeroEstudante;
    @Column(length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O campo Ano Curricular é obrigatório!")
    private AnoCurricular anoCurricular;
    @Temporal(TemporalType.DATE)
    private Date dataMatricula = new Date();
    @ManyToOne
    @JoinColumn(nullable = false)
    private Aluno aluno;
    @ManyToOne
    //@JsonIgnore
    @JoinColumn(nullable = false)
    @NotNull(message = "O campo turma é obrigatório!")
    private Turma turma;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O situacao Estudante é obrigatório!")
    @Column(nullable = false, length = 13)
    private SituacaoMatricula situacao;
    @OneToMany
    private Set<Emolumento> emolumentos;

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Integer getAnoAcademico() {
        return anoAcademico;
    }

    public void setAnoAcademico(Integer anoAcademico) {
        this.anoAcademico = anoAcademico;
    }

    public AnoCurricular getAnoCurricular() {
        return anoCurricular;
    }

    public void setAnoCurricular(AnoCurricular anoCurricular) {
        this.anoCurricular = anoCurricular;
    }

    public String getNumeroEstudante() {
        return numeroEstudante;
    }

    public void setNumeroEstudante(String numeroEstudante) {
        this.numeroEstudante = numeroEstudante;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public SituacaoMatricula getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoMatricula situacao) {
        this.situacao = situacao;
    }

    public Set<Emolumento> getEmolumentos() {
        return emolumentos;
    }

    public void setEmolumentos(Set<Emolumento> emolumentos) {
        this.emolumentos = emolumentos;
    }
    

}
