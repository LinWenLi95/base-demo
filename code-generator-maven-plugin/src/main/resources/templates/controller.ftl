package ${codeConfig.controllerPack};

import com.lwl.base.code.common.util.HttpRequestUtil;
import com.lwl.base.code.common.vo.Page;
import com.lwl.base.code.common.vo.Result;
import com.lwl.base.code.common.vo.ResultCode;
import ${codeConfig.beanPack}.${beanName?cap_first};
import ${codeConfig.servicePack}.${beanName?cap_first}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * ${beanDesc}Controller
 * @author ${copyRight.author}
 * @date ${copyRight.since?string("yyyy/MM/dd")}
 */
@RestController
@RequestMapping("/${beanName}")
public class ${beanName?cap_first}Controller {

    @Autowired
    ${beanName?cap_first}Service ${beanName}Service;


    /**http://localhost:8080/${beanName}s?current=1&limit=10&order_by=id&sort=asc
     * 查询多条数据
     * @param request 请求对象，以下请求参数：<br/>
     * T对象的属性名（可选,所有属性都可作为条件）<br/>
     * current 当前页码（可选，必须与limit配合使用）<br/>
     * limit 取出数量（可选，可单独使用）<br/>
     * order_by 排序字段（可选，必须与sort配合使用）<br/>
     * sort 排序方式（可选，值选填：asc|desc，必须与order_by配合使用）
     * @return Result<Page<${beanName?cap_first}>>
     */
    @GetMapping("/")
    public Result<Page<${beanName?cap_first}>> queryList(HttpServletRequest request) {
        // 将请求参数集合取出
        Map<String, Object> parameterMap = HttpRequestUtil.getParameterMap(request);
        Page<${beanName?cap_first}> page = ${beanName}Service.queryPage(parameterMap, null);
        return Result.success(page);
    }

    /**
     * 查询单条数据
     * @param id 主键
     * @return Result<${beanName?cap_first}>
     */
    @GetMapping("/{id}")
    public Result<${beanName?cap_first}> queryOne(@PathVariable("id") Integer id) {
        ${beanName?cap_first} t = ${beanName}Service.queryById(id, null);
        return Result.success(t);
    }

    /**
     * 新增数据
     * @param obj 要添加的参数
     * @return Result<Object>
     */
    @PostMapping("/")
    public Result<Object> add(@RequestBody ${beanName?cap_first} obj) {
        Integer result = 0;
        if (obj != null) {
            result = ${beanName}Service.add(obj);
        }
        return result > 0 ? Result.success() : Result.success(ResultCode.UNEXPECTED_RESULTS);
    }

    /**
     * 更新数据
     * @param obj 要更新的参数
     * @return Result<Object>
     */
    @PutMapping("/")
    public Result<Object> update(@RequestBody ${beanName?cap_first} obj) {
        Integer result = 0;
        if (obj != null) {
            result = ${beanName}Service.edit(obj);
        }
        return result == 1 ? Result.success() : Result.success(ResultCode.UNEXPECTED_RESULTS);
    }

    /**
     * 删除数据
     * @param id 主键
     * @return Result<Object>
     */
    @DeleteMapping("/{id}")
    public Result<Object> del(@PathVariable("id") Integer id) {
        Integer result = 0;
        if (id != null) {
            result = ${beanName}Service.remove(id);
        }
        return result == 1 ? Result.success() : Result.success(ResultCode.UNEXPECTED_RESULTS);
    }
}