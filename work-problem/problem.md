###遇到的问题
###1、maven打包之后，导致ttf文件的字体失效

解决方法忽略
```
 <!-- resources插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>2.6</version>
                <configuration>
                    <useDefaultDelimiters>true</useDefaultDelimiters>
                    <nonFilteredFileExtensions>
                        <nonFilteredFileExtension>ttf</nonFilteredFileExtension>
                    </nonFilteredFileExtensions>
                </configuration>
            </plugin>

```