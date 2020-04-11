### 自定义代码生成器（Maven插件）使用说明
* 插件功能：获取MySQL数据库表信息，根据预设的模板文件生成对应的Java代码文件
* 适用数据库：MySQL
* 模板引擎：FreeMarker

#### 实现原理/步骤：
1. 在application.yml中自定义配置信息：
    * JDBC配置
    * 代码生成位置配置
    * 作者/版本配置
    * 要生成代码的表名
2. 将application.yml中的配置读取到YamlConfigUtil类下对应的配置类的实例中
3. 通过jdbc获取数据库表信息（表名、表描述、列名、列数据类型、列描述）
4. 将数据库表的列数据类型转换为Java数据类型，数据库表信息转化结果数据用Java对象保存
5. FreeMarker读取ftl模板文件
6. 将数据库表信息的Java对象数据与模板进行合并，并输出代码文件到指定位置

#### 代码生成器使用：
1. clone代码生成器代码到本地，使用ide工具以maven项目打开
2. 编译插件到Maven仓库（编译前可对代码进行自定义修改）
    * 运行maven->Lifecycle中的install命令编译
    * 注意不要用maven->plugins中的install，否则其他项目引入插件时会无法解析
3. 添加Maven插件到其他项目
    * 在其他Maven项目pom.xml文件的project->build->plugins标签中引入自定义插件依赖
    * 若使用默认配置，将配置写在application.yml中，可不写configuration标签
```xml
<plugin>
    <groupId>com.lwl</groupId>
    <artifactId>code-generator-maven-plugin</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <!--若使用默认配置，可不写configuration标签-->
    <configuration>
        <!--项目路径,未指定则默认调用System.getProperty("user.dir")来获取-->
        <path>${project.basedir}</path>
        <!--指定配置文件,未指定则默认为src\main\resources\application.yml-->
        <configFilePath>src\main\resources\application.yml</configFilePath>
    </configuration>
</plugin>
```
4. 在yml配置文件中进行配置
```yaml
code-generator:
  # mysql connection
  jdbc:
    driverClass: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_test?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true
    userName: root
    password: root
  # 要生成的表名,英文逗号隔开(放空则生成全部表)
  tables: user,role,menu
  # code gen pack
  file-path:
    # outputBase放空则代码生成到调用插件的项目中
    outputBase: D:\code
    packBase: com.lwl.base.code.generator.demo
    packController: controller
    packService: service
    packDao: dao
    # 添加'classpath:'前缀时,将*.xml映射文件生成在resources/mapper资源路径下
    # 未添加'classpath:'前缀时,*.xml映射文件生成在packBase基础包路径下的mapper包中
    packMapper: classpath:mapper
    packBean: entity.pojo
    packWeb: web
    packApi: api
  copyright:
    author: LinWenLi
    version: 1.0
```
5. 运行自定义插件
- 引入代码生成器插件的项目执行maven命令：`mvn code:generate`
- 执行完成查看是否生成代码文件到指定位置

> 说明：
* Maven插件调用命令格式：`mvn goalPrefix:MojoName`
* goalPrefix表示pom.xml中goalPrefix属性的值
* MojoName表示@Mojo注解的name属性
* 以代码生成器为例：`mvn code:generate`

附：
* 如果要自定义生成代码的模板文件可在引用代码生成器插件的项目的resources目录下创建templates文件夹，在templates下放置以下名称的ftl文件则可覆盖代码生成器插件中默认的*.ftl.
* 如果想直接运行代码生成器插件项目进行生成代码，可在配置完application.yml后将CodeGenerateMojo的execute()方法改成main方法直接运行
* 如果想生成其他数据库的表，需要将实现原理/步骤的3、4酌情修改
```
bean.ftl
controller.ftl
dao.ftl
mapper.ftl
service.ftl
serviceImpl.ftl
```
