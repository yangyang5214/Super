package com.springboot.common.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Specifies the authority permitted to access method(s) in an application. The
 * value of the AuthorityAllowed annotation is the id of Authority entity. This
 * annotation can be specified on a class or on method(s). Specifying it at a
 * class level means that it applies to all the methods in the class. Specifying
 * it on a method means that it is applicable to that method only. If applied at
 * both the class and methods level , the method value overrides the class value
 * if the two conflict.
 */

@Documented
@Retention(RUNTIME)
@Target({ FIELD })
public @interface ExcelHeadMap {
	String name();
	ExcelCellType type() default ExcelCellType.defaultType;
}