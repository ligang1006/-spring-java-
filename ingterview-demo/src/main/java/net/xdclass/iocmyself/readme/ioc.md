##自己实现IOC容器，这里是最简单的方式  
##*框架的学习方法*

先看做了什么，大致的脉络思路  


原理使用反射
![img.png](img.png)  

map实现Bean的注入

map放东西由Spring帮我们创建  

###IOC容器的管理
IOC容器中的对象都是按照需求创建的  
xml文件和注解等方式，把这些东西（BeanDefinition，源码有BeanDefinitionReader）放到容器中
![img_1.png](img_1.png)
BeanDefinitionRegistry 注册Bean
![img_3.png](img_3.png)

事例化好之后

如果要将某一个属性值做修改的话，有100个bean需要设置某个属性（修改bean）

所以DeanDefinition和事例化对象直接还有别的处理  
打开xml？？
不实际太慢
![img_4.png](img_4.png)

BeanDefinition和BeanFactory之间也有东西
下图是BeanFactoryPostProcessor**这里完成了拓展增强的功能**

![img_5.png](img_5.png)
![img_6.png](img_6.png)  

internalConfigurationProcessor
![img_7.png](img_7.png)

@SpringBootApplication->@EnableAutoConfiguration
![img_8.png](img_8.png)
AutoConfigurationImportSelector
```
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
getBeanClassLoader());
Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
+ "are using a custom packaging, make sure that file is correct.");
return configurations;
}
```
getSpringFactoriesLoaderFactoryClass方法
```
protected Class<?> getSpringFactoriesLoaderFactoryClass() {
		return EnableAutoConfiguration.class;
	}
```

![img_9.png](img_9.png)  
这里面又一个配置
![img_10.png](img_10.png)

![img_11.png](img_11.png)
getCandidateConfigurations方法返回的configurations与spring.factories配置的相同


不应该从注解那开始入手分享，网上倒，直到上面有一个spring的refresh方法，
会执行BeanFactoryPostProcessor类，这个类会解析import注解，之后解析

![img_12.png](img_12.png)


*实例化*：只是调用构造方法，在对堆里面开辟存储空间，其中的属性并没有付值操作  
![img_13.png](img_13.png)

初始化：