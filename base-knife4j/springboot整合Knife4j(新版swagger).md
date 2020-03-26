## springboot整合Knife4j(新版swagger)

* 1.pom.xml添加依赖
```xml
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>2.0.2</version>
</dependency>
```
* 2.swagger配置类
```Java
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean(value = "defaultApi2")
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .groupName("2.X版本")
                .select()
                //这里指定要扫描的Controller包路径
                .apis(RequestHandlerSelectors.basePackage("com.lwl.base.knife4j.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("接口文档")
                        .description("xx系统的接口文档")
                        .version("1.0")
                        .contact(new Contact("LinWenLi", "www.baidu.com", "1318399699@qq.com"))
                        .license("许可证")
                        .licenseUrl("http://www.baidu.com")
                        .build());
    }

}
```
* 3.在controller接口方法上添加说明
```java
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试接口")
public class TestController {

    @ApiOperation(value = "获取数据")
    @GetMapping("/get/{key}")
    public String set(@ApiParam(value = "这是key的字段说明",required = true) @PathVariable("key") String key) {
        return String.format("key:%s", key);
    }

    @ApiOperation(value = "提交数据")
    @PostMapping("/post/{key}/{value}")
    public String get(@ApiParam(value = "这是key的字段说明",required = true) @PathVariable("key") String key,
                      @ApiParam(value = "这是value的字段说明",required = true) @PathVariable("value") String value) {
        return String.format("key:%s，value：%s", key, value);
    }
}
```
诸如@Api/@ApiOperation等的注解使用可参考https://doc.xiaominfo.com/knife4j/的说明
##### 注：项目在上生产环境时可在application.yml中配置`knife4j.production=true`，关闭接口文档的访问

项目启动后浏览器访问[接口文档链接](http://localhost:8080/doc.html "接口文档")