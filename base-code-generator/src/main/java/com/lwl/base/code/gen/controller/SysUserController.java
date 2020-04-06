package com.lwl.base.code.gen.controller;
import	java.util.Map;

import com.lwl.base.code.common.util.HttpRequestUtil;
import com.lwl.base.code.common.vo.Page;
import com.lwl.base.code.common.vo.Result;
import com.lwl.base.code.common.vo.ResultCode;
import com.lwl.base.code.gen.entity.pojo.SysUser;
import com.lwl.base.code.gen.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 系统 用户表Controller
 * @author LinWenLi
 * @date 2020/04/05
 */
@RestController
@RequestMapping("/users")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    /**http://localhost:8080/users?salt=1&current=1&limit=10&order_by=id&sort=asc
     * 查询多条数据
     * @param request 请求对象，以下请求参数：<br/>
     * T对象的属性名（可选,所有属性都可作为条件）<br/>
     * current 当前页码（可选，必须与limit配合使用）<br/>
     * limit 取出数量（可选，可单独使用）<br/>
     * order_by 排序字段（可选，必须与sort配合使用）<br/>
     * sort 排序方式（可选，值选填：asc|desc，必须与order_by配合使用）
     * @return Result<Page<SysUser>>
     */
    @GetMapping
    public Result<Page<SysUser>> queryList(HttpServletRequest request) {
        // 将请求参数集合取出
        Map<String, Object> parameterMap = HttpRequestUtil.getParameterMap(request);
        Page<SysUser> page = sysUserService.queryPage(parameterMap, null);
        return Result.success(page);
    }

    /**http://localhost:8080/users/4
     * 查询单条数据
     * @param id 主键
     * @return Result<SysUser>
     */
    @GetMapping("/{id}")
    public Result<SysUser> queryOne(@PathVariable("id") Integer id) {
        SysUser t = sysUserService.queryById(id, null);
        return Result.success(t);
    }

    /**
     * 新增数据
     * @param obj 要添加的参数
     * @return Result<Object>
     */
    @PostMapping("/")
    public Result<Object> add(@RequestBody SysUser obj) {
        Integer result = 0;
        if (obj != null) {
            result = sysUserService.add(obj);
        }
        return result > 0 ? Result.success() : Result.success(ResultCode.UNEXPECTED_RESULTS);
    }

    /**
     * 更新数据
     * @param obj 要更新的参数
     * @return Result<Object>
     */
    @PutMapping("/")
    public Result<Object> edit(@RequestBody SysUser obj) {
        Integer result = 0;
        if (obj != null) {
            result = sysUserService.edit(obj);
        }
        return result == 1 ? Result.success() : Result.success(ResultCode.UNEXPECTED_RESULTS);
    }

    /**
     * 删除数据
     * @param id 主键
     * @return Result<Object>
     */
    @DeleteMapping("/{id}")
    public Result<Object> remove(@PathVariable("id") Integer id) {
        Integer result = 0;
        if (id != null) {
            result = sysUserService.remove(id);
        }
        return result == 1 ? Result.success() : Result.success(ResultCode.UNEXPECTED_RESULTS);
    }
}