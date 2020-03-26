package com.lwl.base.mysql.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* 系统 菜单表
* @author lwl
* @since 2019/12/15
*/
@Data
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /***/
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**父菜单id（一级菜单的父id默认为0）*/
    private Integer parentId;
    /**菜单中文名称*/
    private String name;
    /**菜单英文名称*/
    private String enname;
    /**url地址(http请求类型全部为GET)*/
    private String url;
    /**菜单图标样式*/
    private String icon;
    /**状态 0禁用,1启动*/
    private Integer state;
    /**是否已删除*/
    private Boolean isDel;
    /**备注*/
    private String description;
    /**创建者id*/
    private Integer creatorId;
    /**创建时间*/
    private Timestamp createTime;
    /**更新者id*/
    private Integer updaterId;
    /**更新时间*/
    private Timestamp updateTime;

    /**是否根节点*/
    public boolean isRoot() {
        return parentId.equals(0);
    }
}
