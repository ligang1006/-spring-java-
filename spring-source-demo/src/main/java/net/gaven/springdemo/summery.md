###iocmyself 自定义实现注解 + spring源码读取笔记

###prepareloadpropertiesbeforestart 项目启动之前进行加载的实现方法

###mybeanfactorypostprocessor BeanFactoryPostProcessor的使用方法

### reflection 扫描全包的方法 Reflection工具的使用

###spring中的一些继承和实现关系
![img.png](img.png)
standardEnvironment 是prepareRefresh()方法创建的环境，如果private ConfigurableEnvironment environment;
environment为空则创建一个标准的环境  
**PropertySource：属性源。**    key-value属性对抽象   


**PropertyResolver：属性解析器。** 用于解析相应key的value

![img_4.png](img_4.png)
DefaultListableBeanFactory
// Tell the subclass to refresh the internal bean factory.
ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
创建的默认工厂
AbstractRefreshableApplicationContext创建


![img_3.png](img_3.png)