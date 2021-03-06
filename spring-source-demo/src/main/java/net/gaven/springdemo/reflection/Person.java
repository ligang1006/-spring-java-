package net.gaven.springdemo.reflection;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.Bean;

/**
 * @author: lee
 * @create: 2021/5/31 9:48 上午
 **/

public class Person implements BeanNameAware {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void setBeanName(String name) {
        this.name=name;
    }
}
