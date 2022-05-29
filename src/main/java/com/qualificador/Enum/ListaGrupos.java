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
public enum ListaGrupos {
    Empregador("Empregador"),
    Usuario("Usuario"),
    Administrador("Administrador");
    private String listaGrupos;

    private ListaGrupos(String listaGrupos) {
        this.listaGrupos = listaGrupos;

    }

}
