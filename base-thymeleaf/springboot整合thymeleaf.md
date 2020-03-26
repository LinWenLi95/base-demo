## springboot整合thymeleaf
* 1.pom.xml添加依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
* 2.application.yml配置thymeleaf
```yaml
# thymeleaf
spring:
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html
    encoding: utf-8
    mode: HTML5
    check-template-location: true
    cache: false
    servlet:
      content-type: text/html
```
* 3.在resources下创建templates文件夹，并创建index.html
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>Hello thymeleaf!</h1>
</body>
</html>
```
* 4.controller创建方法用于接收请求并跳转到index.html
```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/get")
    public String get() {
        return "index";
    }
}
```