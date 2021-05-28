package net.gaven.springdemo.mybeanfactorypostprocessor;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author: lee
 * @create: 2021/5/28 4:16 下午
 **/
public class MyFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {

        return this.getObjectType();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
