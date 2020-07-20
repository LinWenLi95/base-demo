package com.jdbc.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author LinWenLi
 * @since 2020-07-20
 */
@Service
public class DemoService {

    @Autowired
    @Qualifier("jdbcTemplateTwo")
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> get() {
        String sql = "select * from table2";
        return jdbcTemplate.queryForList(sql);
    }
}
