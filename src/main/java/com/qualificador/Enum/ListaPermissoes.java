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
public enum ListaPermissoes {
    RECRUTAR("Recrutar"),
    PERFIL_PROFISSIONAL("Perfil ProfissIonal"),
    CONTACTAR_ADMIN("Contactar Admin"),
    CADASTRAR_USUARIOS("Cadastrar Usuários");
    private String listaPermissao;

    private ListaPermissoes(String listaPermissao) {
        this.listaPermissao = listaPermissao;

    }

}
