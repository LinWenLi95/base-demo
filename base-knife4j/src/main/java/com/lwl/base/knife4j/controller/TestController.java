package com.lwl.base.knife4j.controller;

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
