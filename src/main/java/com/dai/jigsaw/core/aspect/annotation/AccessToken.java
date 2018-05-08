package com.dai.jigsaw.core.aspect.annotation;

import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;  
  
/** 
 * token注解 
 */  
@Target(ElementType.METHOD)//这个注解是应用在方法上  
@Retention(RetentionPolicy.RUNTIME)  
public @interface AccessToken {  
}  
