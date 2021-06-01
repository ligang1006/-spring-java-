##reflection的使用
引入相关的依赖
```
<dependency>
  <groupId>org.reflections</groupId>
  <artifactId>reflections</artifactId>
  <version>0.9.10</version>
</dependency>
```
Reflections通过扫描classpath，索引元数据，并且允许在运行时查询这些元数据。

使用Reflections可以很轻松的获取以下元数据信息：

获取某个类型的所有子类；比如，有一个父类是TestInterface，可以获取到TestInterface的所有子类。
获取某个注解的所有类型/字段变量，支持注解参数匹配。
使用正则表达式获取所有匹配的资源文件
获取特定签名方法。
项目中使用：
```
public class ReflectionTest {
    public static void main(String[] args) {
        // 扫包
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("com.boothsun.reflections") // 指定路径URL
                .addScanners(new SubTypesScanner()) // 添加子类扫描工具
                .addScanners(new FieldAnnotationsScanner()) // 添加 属性注解扫描工具
                .addScanners(new MethodAnnotationsScanner() ) // 添加 方法注解扫描工具
                .addScanners(new MethodParameterScanner() ) // 添加方法参数扫描工具
                );

        // 反射出子类
        Set<Class<? extends ISayHello>> set = reflections.getSubTypesOf( ISayHello.class ) ;
        System.out.println("getSubTypesOf:" + set);

        // 反射出带有指定注解的类
        Set<Class<?>> ss = reflections.getTypesAnnotatedWith( MyAnnotation.class );
        System.out.println("getTypesAnnotatedWith:" + ss);

        // 获取带有特定注解对应的方法
        Set<Method> methods = reflections.getMethodsAnnotatedWith( MyMethodAnnotation.class ) ;
        System.out.println("getMethodsAnnotatedWith:" + methods);

        // 获取带有特定注解对应的字段
        Set<Field> fields = reflections.getFieldsAnnotatedWith( Autowired.class ) ;
        System.out.println("getFieldsAnnotatedWith:" + fields);

        // 获取特定参数对应的方法
        Set<Method> someMethods = reflections.getMethodsMatchParams(long.class, int.class);
        System.out.println("getMethodsMatchParams:" + someMethods);

        Set<Method> voidMethods = reflections.getMethodsReturn(void.class);
        System.out.println( "getMethodsReturn:" + voidMethods);

        Set<Method> pathParamMethods =reflections.getMethodsWithAnyParamAnnotated( PathParam.class);
        System.out.println("getMethodsWithAnyParamAnnotated:" + pathParamMethods);
    }
}
```