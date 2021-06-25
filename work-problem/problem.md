###遇到的问题
###1、maven打包之后，导致ttf文件的字体失效

最近项目遇到一个莫名其妙的问题，本地测试没问题，打包成war包之后，文字就变了经过查询找到的问题产生的原因，并记录下来

maven打包时，有些资源文件被编译后会与原文件不同，导致文件不可用
解决方法是配置maven打包时，不编译指定类型的资源文件，如下：
```
<plugin>
<artifactId>maven-resources-plugin</artifactId>
<configuration>
<nonFilteredFileExtensions>
<!-- 不需要编译的资源文件 -->
<nonFilteredFileExtension>ttf</nonFilteredFileExtension>
<nonFilteredFileExtension>zip</nonFilteredFileExtension>
</nonFilteredFileExtensions>
</configuration>
</plugin>
```

nonFilteredFileExtension和exclude的区别
需要注意<nonFilteredFileExtension>和<exclude>的使用区别
<exclude>指定的文件不会编译也不会打入war包。
<nonFilteredFileExtension>指定的文件不会编译，但会打入war包。
###2、markdown 实现链接的方法
格式为 [link text](URL 'title text')。

① 普通链接：

[Google2](http://www.google.com/)
Google

② 指向本地文件的链接：

[icon.png](./images/icon.png)
icon.png

③ 包含 'title' 的链接:

[Google2](http://www.google.com/ "Google1")
Google

title 使用 ' 或 " 都是可以的。



## 标题
跳转到[链接](#标题)
###
###
###
###
###
###
###
###