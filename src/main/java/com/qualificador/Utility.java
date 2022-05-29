/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author PEDRO P MULENGA
 */
public class Utility {

    public static String getSystemURL(HttpServletRequest request) {
        String systemURL = request.getRequestURL().toString();
        return systemURL.replace(request.getServletPath(), "");
    }

}
