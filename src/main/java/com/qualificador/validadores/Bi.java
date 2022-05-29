/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.validadores;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

/**
 *
 * @author TECNO BASE
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy={})
@Pattern(regexp = "(\\d{9}[a-zA-Z]{2}\\d{3})", message = "O B.I deve corresponder ao padão 999999999XX999")
public @interface Bi {
    String message() default "{com.ispi.projectoIspi.validadores.bi}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
