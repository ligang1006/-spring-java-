# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.0/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

#spring 揭秘笔记
 

###chapter2
就反转在让你从原来的事必躬亲，转变为现在的享受服务,所以，简单点儿说，IoC的理念就是，
让别人为你服务！在图2-1中，也就是让IoC Service Provider来为你服务！  

![img.png](chapter2/img.png)  
通常情况下，被注入对象会直接依赖于被依赖对象。但是，在IoC的场景中，二者之间通过IoC Service
Provider来打交道，所有的被注入对象和依赖对象现在由IoC Service Provider统一管理。被注入对象需要
什么，直接跟IoC Service Provider招呼一声，后者就会把相应的被依赖对象注入到被注入对象中，从而
达到IoC Service Provider为被注入对象服务的目的。IoC Service Provider在这里就是通常的IoC容器所充
当的角色。从被注入对象的角度看，与之前直接寻求依赖对象相比，依赖对象的取得方式发生了反转，
控制也从被注入对象转到了IoC Service Provider那里

###查询方式
不管怎样，你终究会找到一种方式来向服务生表达你的需求，以便他为你提供适当的服务。那么，
在IoC模式中，被注入对象又是通过哪些方式来通知IoC Service Provider为其提供适当服务的呢？ 
IoC模式最权威的总结和解释，应该是Martin Fowler的那篇文章“Inversion of Control Containers and
the Dependency Injection pattern”，    
其中提到了三种依赖注入的方式，即构造方法注入（constructor
injection）、setter方法注入（setter injection）以及接口注入（interface injection）。下面让我们详细看
一下这三种方式的特点及其相互之间的差别。  
####1、构造方法注入
是被注入对象可以通过在其构造方法中声明依赖对象的参数列表，
让外部（通常是IoC容器）知道它需要哪些依赖对象。chaper2-1 FXNewsProvider
```
public FXNewsProvider(IFXNewsListener newsListner,IFXNewsPersister newsPersister) 
{ 
 this.newsListener = newsListner; 
this.newPersistener = newsPersister; 
}
```
IoC Service Provider会检查被注入对象的构造方法，取得它所需要的依赖对象列表，进而为其注
入相应的对象。同一个对象是**不可能被构造两次**的，因此，被注入对象的构造乃至其整个生命周期，
应该是由IoC Service Provider来管理的。
构造方法注入方式比较直观，对象被构造完成后，即进入就绪状态，可以马上使用。这就好比你
刚进酒吧的门，服务生已经将你喜欢的啤酒摆上了桌面一样。坐下就可马上享受一份清凉与惬意。
####2、setter方法注入 **推荐的方法**



####3、接口注入


### 4.2 重点！！！  BeanFactory的对象注册与依赖绑定方式
####方法1：直接编码方式

**通过书架BeanDefinitionRegistry 把书的定义 RootBeanDefinition（负责保存对象的所有必要信息） 注册**
```
public static void main(String[] args) 
{ 
 DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory(); 
 BeanFactory container = (BeanFactory)bindViaCode(beanRegistry); 
 FXNewsProvider newsProvider = ➥
 (FXNewsProvider)container.getBean("djNewsProvider"); 
 newsProvider.getAndPersistNews(); 
} 
public static BeanFactory bindViaCode(BeanDefinitionRegistry registry) 
{ 
 AbstractBeanDefinition newsProvider = ➥
 new RootBeanDefinition(FXNewsProvider.class,true); 
 AbstractBeanDefinition newsListener = ➥
 new RootBeanDefinition(DowJonesNewsListener.class,true); 
 AbstractBeanDefinition newsPersister = ➥
 new RootBeanDefinition(DowJonesNewsPersister.class,true); 
 // 将bean定义注册到容器中
 //通过书架 把书的定义注册
 registry.registerBeanDefinition("djNewsProvider", newsProvider); 
 registry.registerBeanDefinition("djListener", newsListener); 
 registry.registerBeanDefinition("djPersister", newsPersister); 
// 指定依赖关系
 // 1. 可以通过构造方法注入方式
 ConstructorArgumentValues argValues = new ConstructorArgumentValues(); 
 argValues.addIndexedArgumentValue(0, newsListener); 
 argValues.addIndexedArgumentValue(1, newsPersister); 
 newsProvider.setConstructorArgumentValues(argValues); 
 // 2. 或者通过setter方法注入方式  
 MutablePropertyValues propertyValues = new MutablePropertyValues(); 
 propertyValues.addPropertyValue(new ropertyValue("newsListener",newsListener)); 
 propertyValues.addPropertyValue(new PropertyValue("newPersistener",newsPersister)); 
 newsProvider.setPropertyValues(propertyValues); 
 2 // 绑定完成
 return (BeanFactory)registry; 
}
```

BeanDefinition--》书架DeanDefinition--》注册

####方法2：外部配置文件方式

Properties文件格式和XML文件---> BeanDefinitionReader--读取到-->BeanDefinition---->DeanDefinitionRegister（架子）完成Bean的注册和加载  
伪代码
```
BeanDefinitionRegistry beanRegistry = <某个BeanDefinitionRegistry实现类，通常为➥
DefaultListableBeanFactory>; 
BeanDefinitionReader beanDefinitionReader = new BeanDefinitionReaderImpl(beanRegistry); 
beanDefinitionReader.loadBeanDefinitions("配置文件路径"); 
// 现在我们就取得了一个可用的BeanDefinitionRegistry实例
```
###方法3：注解的方式

Spring 2.5发布的基于注解的依赖注入方式，如果不使用classpath-scanning功能的话，仍然部分
依赖于“基于XML配置文件”的依赖注入方式。  
@Autowired是这里的主角，它的存在将告知Spring容器需要为当前对象注入哪些依赖对象。而
@Component则是配合Spring 2.5中新的classpath-scanning功能使用的。现在我们只要再向Spring的配置
文件中增加一个“触发器”，使用@Autowired和@Component标注的类就能获得依赖对象的注入了。
![img.png](img.png)

####xml配置讲解
1.《bean》之唯我独尊  
   《beans>是XML配置文件中最顶层的元素，它下面可以包含0或者1个<description>和多个
   <bean>以及<import>或者<alias>，如图4-4所示。
![img_1.png](img_1.png)

