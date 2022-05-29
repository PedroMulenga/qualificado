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
public enum TituloAcademico {
    Tecnico_Medio("Técnico Médio"),
    Licenciado("Licenciado"),
    Doutor("Doutor"),
    Mestre("Mestre"),
    Nenhum("Nenhum");

    private String tituloAcademico;

    private TituloAcademico(String tituloAcademico) {
        this.tituloAcademico = tituloAcademico;

    }

}
