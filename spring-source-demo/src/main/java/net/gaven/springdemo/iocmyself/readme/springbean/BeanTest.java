package net.gaven.springdemo.iocmyself.readme.springbean;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.applet.Main;

/**
 * aware 接口的使用例子
 *
 * @author: lee
 * @create: 2021/6/8 11:15 上午
 **/
public class BeanTest {

//    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

//        BeanDefinitionRegistry beanDefinitionRegistry=new
//                Object a = applicationContext.getBean("a");
//        A a1 = null;
//        if (a instanceof A) {
//            a1 = (A) a;
//            System.out.println("得到beanFactory对象 " + a1.getBeanFactory());
//            System.out.println();
//        }
//    }

    public static void main(String[] args) {

        MyClassPathXmlApplicationContext context=new MyClassPathXmlApplicationContext("applicationContext.xml");
//        System.out.println(name);
    }
}
