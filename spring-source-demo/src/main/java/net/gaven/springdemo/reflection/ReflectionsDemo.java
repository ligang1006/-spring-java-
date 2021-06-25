package net.gaven.springdemo.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Reflections 的使用
 *
 * @author: lee
 * @create: 2021/5/31 9:43 上午
 **/

public class ReflectionsDemo {
    //    public static void main(String[] args) {
//        givenObject_whenGetsFieldNamesAtRuntime_thenCorrect();
//    }
//
//    public static void givenObject_whenGetsFieldNamesAtRuntime_thenCorrect() {
//        Object person = new Person();
//        Field[] declaredFields = person.getClass().getDeclaredFields();
//        Set<String> set = new LinkedHashSet<>();
//        Stream.of(declaredFields).forEach(field -> {
//            String name = field.getName();
//            set.add(name);
//        });
//
//
//    }
    @Autowired
    static ScannerDemo scannerDemo;

    @MyAnnotation
    static void testMethod() {
        scannerDemo.setOption("eee");
        String option = scannerDemo.getOption();
        System.out.println(option);

    }

    public static void main(String[] args) {
        // 扫包
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                // 指定路径URL
                .forPackages("net.gaven.springdemo.reflection")
                // 添加子类扫描工具
                .addScanners(new SubTypesScanner())
                // 添加 属性注解扫描工具
                .addScanners(new FieldAnnotationsScanner())
                // 添加 方法注解扫描工具
                .addScanners(new MethodAnnotationsScanner())
                // 添加方法参数扫描工具
                .addScanners(new MethodParameterScanner())
        );

        // 反射出子类
        Set<Class<? extends Person>> set = reflections.getSubTypesOf(Person.class);
        System.out.println("getSubTypesOf:" + set);

        // 反射出带有指定注解的类
        Set<Class<?>> ss = reflections.getTypesAnnotatedWith(MyAnnotation.class);
        System.out.println("getTypesAnnotatedWith:" + ss);

        // 获取带有特定注解对应的方法
        Set<Method> methods = reflections.getMethodsAnnotatedWith(MyAnnotation.class);
        System.out.println("getMethodsAnnotatedWith:" + methods);

        // 获取带有特定注解对应的字段
        Set<Field> fields = reflections.getFieldsAnnotatedWith(Autowired.class);
        System.out.println("getFieldsAnnotatedWith:" + fields);

        // 获取特定参数对应的方法
        Set<Method> someMethods = reflections.getMethodsMatchParams(long.class, int.class);
        System.out.println("getMethodsMatchParams:" + someMethods);

//        Set<Method> voidMethods = reflections.getMethodsReturn(void.class);
//        System.out.println("getMethodsReturn:" + voidMethods);

//        Set<Method> pathParamMethods = reflections.getMethodsWithAnyParamAnnotated(PathParam.class);
//        System.out.println("getMethodsWithAnyParamAnnotated:" + pathParamMethods);
    }

}
