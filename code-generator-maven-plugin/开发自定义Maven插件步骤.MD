编写自定义Maven插件步骤：
1. 创建Maven插件项目：
* New Project -> 选择创建Maven项目 -> 选中`org.apache.maven.archetypes:maven-archetype-mojo` -> 确定包名组织名 -> 创建项目
* 创建的Maven插件项目有pom.xml文件和src/main/java/包名/MyMojo.java文件
2. 在pom.xml中添加自定义插件所需要的插件及依赖
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.demo</groupId>
    <artifactId>my-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>Maven插件</name>

    <!--打包方式必须是maven-plugin-->
    <packaging>maven-plugin</packaging>

    <dependencies>
        <!--Maven插件必须添加的依赖-->
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-plugin-api</artifactId>
            <version>3.6.3</version>
        </dependency>
        <!--加了这个才能在Mojo类上使用@Mojo注解-->
        <dependency>
            <groupId>org.apache.maven.plugin-tools</groupId>
            <artifactId>maven-plugin-annotations</artifactId>
            <version>3.6.0</version>
            <scope>provided</scope>
        </dependency>
        <!--Maven提供的获取资源文件的依赖-->
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-model</artifactId>
            <version>3.6.3</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <!-- Maven编译插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <!--打包Maven插件-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-plugin-plugin</artifactId>
                <version>3.6.0</version>
                <configuration>
                    <!--指定插件前缀，在这里输入你的插件前缀，方便引用插件时调用-->
                    <goalPrefix>myFirstPluginPrefix</goalPrefix>
                </configuration>
            </plugin>

        </plugins>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
            </resource>
        </resources>
    </build>
</project>
```
3. 在Mojo类中添加自己的代码
```java
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * 示例Mojo（注解Mojo的name属性值可自定义）
 * @author LinWenLi
 */
@Mojo(name = "firstPluginMojo")
public class MyFirstMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException {
        getLog().info( "Hello, world." );
    }
}
```
4. 编译自定义插件到Maven仓库
* 运行maven->Lifecycle中的install命令编译
* 注意不要用maven->plugins中的install，否则其他项目引入插件时会无法解析

5. 在其他项目pom.xml的project->build->plugins标签中引入自定义插件
```xml
<plugin>
    <groupId>com.demo</groupId>
    <artifactId>my-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
</plugin>
```
6. 运行自定义插件
- Maven插件调用命令格式：`mvn goalPrefix:MojoName`
    * goalPrefix表示pom.xml中goalPrefix属性的值
    * MojoName表示@Mojo注解的name属性
    * 以MyFirstPlugin为例：`mvn myFirstPluginPrefix:firstPluginMojo`