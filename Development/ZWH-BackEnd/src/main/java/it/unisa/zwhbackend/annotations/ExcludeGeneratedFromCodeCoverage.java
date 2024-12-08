package it.unisa.zwhbackend.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotazione per escludere un metodo dalla copertura del codice. Utilizzata per ignorare
 * specifiche parti del codice nei report di jacoco.
 *
 * <p>Impostato target di utilizzo solo sui metodi, poiché gestiamo l'esclusione di classi
 * centralmente nel pom.xml.
 *
 * @author Marco Renella
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface ExcludeGeneratedFromCodeCoverage {
  // costruttore vuoto
}
