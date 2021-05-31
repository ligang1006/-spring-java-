package net.gaven.springdemo.reflection;

import java.lang.annotation.*;

/**
 * @author: lee
 * @create: 2021/5/31 10:10 上午
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyAnnotation {
}
