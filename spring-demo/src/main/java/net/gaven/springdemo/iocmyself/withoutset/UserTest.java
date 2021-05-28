package net.gaven.springdemo.iocmyself.withoutset;//package net.xdclass.iocmyself.withoutset;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
///**
// * 通过反射实现把userService注册到controller
// *
// * @author: lee
// * @create: 2021/5/27 8:12 下午
// **/
//public class UserTest {
//
//
//    public static void main(String[] args) throws Exception {
//        UserController controller = new UserController();
//
//        UserService userService = new UserService();
//        System.out.println(userService);
//        //获取class对象,三种方式实现
////        Class<?> aClass1 = Class.forName("net.xdclass.iocmyself.getsetmethod.UserController");
//        Class<? extends UserController> aClass = controller.getClass();
//        //获取属性
//        Field userServiceFiled = aClass.getDeclaredField("userService");
//        /*获取方法，通过get方法获取，但是要通过set方法设置属性*/
//        //首先要获取方法名称
//        String name = userServiceFiled.getName();
//        name = "set"
//                + name.substring(0, 1).toUpperCase()
//                + name.substring(1, name.length());
//        //拿到set方法对象
//        //  其中，传入的第一个参数是方法名，
//        //  第二个参数名是方法参数，
//        //  传入这两个参数之后，便可以根据方法名和方法参数通过反射获取带有参数的方法
//        //setUserService()
//        //  public void setUserService(UserService userService) {
//        //        this.userService = userService;
//        //    }
////        Method method = aClass.getDeclaredMethod(name, UserController.class);
//        Method method = aClass.getDeclaredMethod(name, UserService.class);
///**
// *  调用方法 obj，arg  把后面到参数执行到前面到对象中
// *  Method中invoke（Object obj,Object...args）
// *  第一个参数为类的实例，第二个参数为相应函数中的参数，
// *
// *   我想问，我调用的函数本来是一个多参数（参数个数不确定）的函数，应该怎么办？
// *   可以这样调用：method.invoke(object, new Object[][]{new Object[]{obj1, obj2}});
// *   这样相当于object.method(obj1, obj2);
// */
//
//        //第一个参数为类的实例，第二个参数为相应函数中的参数
//        //注入进去了
//        method.invoke(controller, userService);
//        System.out.println(controller.getUserService());
//    }
//
//
//}
