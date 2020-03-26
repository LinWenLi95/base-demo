package com.lwl.base.mysql;

import com.lwl.base.mysql.dao.SysMenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MysqlApplication {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Test
    public void test() {
        Integer count = sysMenuMapper.selectCount(null);
        System.out.println(String.format("count:%s", count));
    }
}
