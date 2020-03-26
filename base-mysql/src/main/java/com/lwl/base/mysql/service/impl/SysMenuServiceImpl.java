package com.lwl.base.mysql.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwl.base.mysql.dao.SysMenuMapper;
import com.lwl.base.mysql.pojo.SysMenu;
import com.lwl.base.mysql.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public BaseMapper<SysMenu> getMapper() {
        return sysMenuMapper;
    }

    @Override
    public Integer add(SysMenu sysMenu) {
        sysMenu.setParentId(0);
        sysMenu.setIsDel(false);
        sysMenu.setCreatorId(0);
        sysMenu.setUpdaterId(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        sysMenu.setCreateTime(timestamp);
        sysMenu.setUpdateTime(timestamp);
        return getMapper().insert(sysMenu);
    }

}
