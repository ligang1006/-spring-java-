package net.gaven.springdemo.iocmyself.readme.spring.aware;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;

/**
 * @author: lee
 * @create: 2021/6/25 3:13 下午
 * Aware使的能够快速的获取所需要的属性
 **/
public class AwareClassTest implements BeanNameAware, BeanClassLoaderAware {
    private String beanName;
    private Integer status;

    private ClassLoader classLoader;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName =name;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader=classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
