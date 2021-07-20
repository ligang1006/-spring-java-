package net.gaven.springdemo.iocmyself.readme.springbean;

import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.StandardContext;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: lee
 * @create: 2021/7/15 9:34 上午
 **/
public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {
    public MyClassPathXmlApplicationContext(String configLocation) {
        super(configLocation);
    }

    /**
     * 扩展
     */
    @Override
    protected void initPropertySources() {
        System.out.println("扩展了initPropertySources方法...");
        //这里从系统环境变量中拿值,例如JAVA_HOME
        getEnvironment().setRequiredProperties("JAVA_HOME");
    }

    /**
     * Customize the internal bean factory used by this context.
     * Called for each refresh() attempt.
     * The default implementation applies this context's
     * "allowBeanDefinitionOverriding" and "allowCircularReferences"
     * settings, if specified.
     * Can be overridden in subclasses to customize any of
     * DefaultListableBeanFactory's settings.
     * 自定义bean是否能覆盖定义和循环依赖问题
     *
     * @param beanFactory
     */
    @Override
    protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
        super.customizeBeanFactory(beanFactory);
    }
}
