/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ispi.projectoIspi.Enum.MesReferente;
import com.ispi.projectoIspi.Enum.SituacaoEmolumento;
import com.ispi.projectoIspi.Enum.TipoEmolumento;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author PEDRO P MULENGA
 */
@SuppressWarnings("serial")
@Entity
public class Emolumento extends GenericDomin {

    @Column(length = 20, nullable = false)
    @NotBlank(message = "O campo mês referente é obrigatório!")
    private String mesReferente;
    @Column(length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O campo Tipo de Emolumento é obrigatório!")
    private TipoEmolumento tipoEmolumento;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPagamento = new Date();
    @ManyToOne
    @JoinColumn(nullable = false)
    private Matricula matricula;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "A situação é obrigatória!")
    @Column(nullable = false, length = 13)
    private SituacaoEmolumento situacao;
    @Column(nullable = false, precision = 7, scale = 2)
    @NotNull(message = "O valor a pagar é obrigatório!")
    private BigDecimal valorApagar;
    @Column(length = 15, nullable = false)
    @NotNull(message = "O campo Ano Académico Referente é obrigatório!")
    private Integer anoAcademicoReferente;

    public String getMesReferente() {
        return mesReferente;
    }

    public void setMesReferente(String mesReferente) {
        this.mesReferente = mesReferente;
    }

    public TipoEmolumento getTipoEmolumento() {
        return tipoEmolumento;
    }

    public void setTipoEmolumento(TipoEmolumento tipoEmolumento) {
        this.tipoEmolumento = tipoEmolumento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public SituacaoEmolumento getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoEmolumento situacao) {
        this.situacao = situacao;
    }

    public BigDecimal getValorApagar() {
        return valorApagar;
    }

    public void setValorApagar(BigDecimal valorApagar) {
        this.valorApagar = valorApagar;
    }

    public Integer getAnoAcademicoReferente() {
        return anoAcademicoReferente;
    }

    public void setAnoAcademicoReferente(Integer anoAcademicoReferente) {
        this.anoAcademicoReferente = anoAcademicoReferente;
    }
    

}
