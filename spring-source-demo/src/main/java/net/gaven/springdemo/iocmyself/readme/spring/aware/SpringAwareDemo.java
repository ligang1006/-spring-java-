package net.gaven.springdemo.iocmyself.readme.spring.aware;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: lee
 * @create: 2021/6/25 3:11 下午
 **/
public class SpringAwareDemo {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        AwareClassTest awareClass = (AwareClassTest) applicationContext.getBean("awareClassTest");
        System.out.println(awareClass.getBeanName());
        ClassLoader classLoader = awareClass.getClassLoader();
        System.out.println(classLoader);
    }
}
