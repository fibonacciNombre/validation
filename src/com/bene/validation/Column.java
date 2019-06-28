
package com.bene.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

	boolean nullable() default true;

	boolean range() default false;

	int min() default 0;

	int max() default 0;
	
	boolean email() default false;
	
	boolean valalphanum() default false;

}
