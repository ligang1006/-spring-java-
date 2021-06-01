package net.gaven.springdemo.iocmyself.withoutset;

import java.util.stream.Stream;

/**
 * @author: lee
 * @create: 2021/5/27 8:47 下午
 **/
public class Test2 {
    public static void main(String[] args) {

        UserController userController = new UserController();
        //获取class
        Class<? extends UserController> clazz = userController.getClass();
        //获取属性
//        Field[] declaredFields = clazz.getDeclaredFields();
        Stream.of(clazz.getDeclaredFields()).forEach(field -> {
            //当发现的属性被@Autowired注解的时，需要注入
            Autowired annotation = field.getAnnotation(Autowired.class);
            if (annotation != null) {

                field.setAccessible(true);
                //目的，获取类型，方便创建具体对象
                Class<?> type = field.getType();
                try {
                    Object o = type.newInstance();
                    // obj – the object whose field should be modified
                    // value – the new value for the field of obj being modified
                    field.set(userController, o);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        });
        System.out.println(userController.getUserService());
    }
}
