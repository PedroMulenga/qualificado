/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Enum;

/**
 *
 * @author PEDRO P MULENGA
 */
public enum Situacao {
    INSCRITO("Inscrito"),
    CANCELADO("Cancelado");
    private String situacao;
    private Situacao(String situacao){
        this.situacao= situacao;
        
    }
    
}
