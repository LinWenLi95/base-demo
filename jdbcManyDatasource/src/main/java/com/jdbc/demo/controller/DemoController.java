package com.jdbc.demo.controller;

import com.jdbc.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author LinWenLi
 * @since 2020-07-20
 */
@RestController
public class DemoController {

    @Autowired
    @Qualifier("jdbcTemplateOne")
    private JdbcTemplate jdbcTemplateOne;

    @Autowired
    private DemoService jdbcService;

    @GetMapping("/test")
    public String test() {
        String sql = "select * from table1";
        List<Map<String, Object>> maps = jdbcTemplateOne.queryForList(sql);
        System.out.println(maps);
        List<Map<String, Object>> x = jdbcService.get();
        System.out.println(x);
        return "hehe";
    }
}
