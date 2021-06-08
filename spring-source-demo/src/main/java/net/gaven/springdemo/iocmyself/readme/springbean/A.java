package net.gaven.springdemo.iocmyself.readme.springbean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Bean;

/**
 * aware接口的使用 impl
 *
 * @author: lee
 * @crea21/6/8 11:25 上午
 **/
public class A implements BeanFactoryAware {
    public void init(){
        System.out.println("正在执行初始化方法init");
    }
    private BeanFactory beanFactory;
    Bear bear;

    public Bear getBear() {
        return bear;
    }

    public void setBear(Bear bear) {
        this.bear = bear;
    }

    /**
     * 重写setBeanFactory方法
     *
     * @param beanFactory
     * @throws BeansException
     * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory(){
        return beanFactory;
    }
}
