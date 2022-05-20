/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.converter;

import com.ispi.projectoIspi.model.Grupo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 *
 * @author PEDRO P MULENGA
 */
public class GrupoConverter implements Converter<String, Grupo> {

    @Override
    public Grupo convert(String codigo) {
        if (!StringUtils.isEmpty(codigo)) {
            Grupo grupo = new Grupo();
            grupo.setCodigo(Long.valueOf(codigo));
            return grupo;
        }
        return null;
    }

}
