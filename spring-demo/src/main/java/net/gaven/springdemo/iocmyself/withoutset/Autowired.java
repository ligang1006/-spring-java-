package net.gaven.springdemo.iocmyself.withoutset;

import java.lang.annotation.*;

/**
 * @author: lee
 * @create: 2021/5/27 8:44 下午
 **/
@Retention(RetentionPolicy.RUNTIME)
//作用的范围是属性
@Target(ElementType.FIELD)
//是否可以被继承实现
@Inherited
@Documented
public @interface Autowired {
}
