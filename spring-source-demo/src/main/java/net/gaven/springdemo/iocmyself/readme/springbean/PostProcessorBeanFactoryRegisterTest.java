package net.gaven.springdemo.iocmyself.readme.springbean;

import javafx.geometry.Pos;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @author: lee
 * @create: 2021/7/7 2:45 下午
 **/
public class PostProcessorBeanFactoryRegisterTest implements BeanDefinitionRegistryPostProcessor {
    /**
     * BeanDefinitionRegistry提供了丰富的方法来操作BeanDefinition，
     * 判断、注册、移除等方法都准备好了，
     * 我们在编写postProcessBeanDefinitionRegistry方法的内容时，
     * 就能直接使用入参registry的这些方法来完成判断和注册、移除等操作。
     *
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    /**
     * 该方法的实现中，主要用来对bean定义做一些改变。
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
