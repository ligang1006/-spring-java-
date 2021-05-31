package net.gaven.springdemo.reflection;

import java.lang.annotation.*;

/**
 * @author: lee
 * @create: 2021/5/31 1:54 下午
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyBean {
}
