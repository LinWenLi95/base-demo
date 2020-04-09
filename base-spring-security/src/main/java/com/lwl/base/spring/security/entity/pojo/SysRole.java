package com.lwl.base.spring.security.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* 系统 角色表
* @author LinWenLi
* @date 2020/04/09
*/
@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /***/
    private Integer id;
    /**角色中文名称*/
    private String name;
    /**角色英文名称*/
    private String enname;
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
}
