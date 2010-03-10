package de.cosmocode.palava.rmi.scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.ScopeAnnotation;

/**
 * Scope annotation for rmi calls.
 *
 * @author Willi Schoenborn
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ScopeAnnotation
public @interface RmiScoped {

}
