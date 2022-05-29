/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author PEDRO P MULENGA
 */
@Component
public class StringToInteger implements Converter<String, Integer> {

    @Override
    public Integer convert(String source) {
        source = source.trim();
        if (source.length() > 0) {
            source = source.replace("-", "");
            return Integer.parseInt(source);
        }
        return 0;
    }

}
