/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mpeg.drosofila.util.visao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Victor Coutinho
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cadastro {

    boolean inclui() default true;

    boolean altera() default true;

    boolean excluir() default true;

    String tituloFormulario();
    
    String codApp() default "2";
}
