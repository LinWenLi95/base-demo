使用Swagger2管理接口文档，为了规范格式，定以下规则：

1. 接收数据的实体全部放在DTO中，实体命名以DTO结尾
2. 返回数据的实体全部放在VO中，实体命名以VO结尾
3. 接收/返回数据的实体全部新建，不直接使用表映射实体
4. 接收/返回数据的实体必须遵循：
    * 类使用`@ApiModel`标记，并补充`description`属性
    * 字段使用`@ApiModelProperty`标记，
        * `value` 字段含义说明
        * `example` 字段值举例
        * `required` 接收数据的字段必填时，必须填此属性，返回数据字段无需填写

实体规范：
```java
import lombok.Data;
import java.sql.Timestamp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@ApiModel(description = "示例类")
public class DemoDTO{
	@ApiModelProperty(value = "名称", example = "李白", required = true)
	private String name;
	@ApiModelProperty(value = "创建时间", example = "1578365878000")
	private Timestamp createTime;
}
```

接口规范：
```java
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "示例接口")
public class DemoController {

    @ApiOperation(value = "示例接口方法")
    @PostMapping(value = "/demo")
    public String demoMethod(@ApiParam(value = "示例请求参数名称")String name){
        return "";
    }
}
```