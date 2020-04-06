package com.lwl.base.code.gen.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* 系统 用户表
* @author LinWenLi
* @date 2020/04/05
*/
@Data
public class SysUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**id*/
    private Integer id;
    /**用户名*/
    private String username;
    // 密码的修改应是单独接口
    /**密码，加密存储*/
    private String password;
    /**手机号*/
    private String telephone;
    /**邮箱*/
    private String email;
    /**状态 0禁用,1启动*/
    private Integer state;
    // isDel字段用于作为逻辑删除字段、查询过滤条件，创建及修改都不由外部接口参数定义
    /**是否已删除*/
    private Boolean isDel;
    /**备注*/
    private String description;
    // 操作人员/时间信息，由系统根据操作进行录入，不由外部接口参数定义
    /**创建者id*/
    private Integer creatorId;
    /**创建时间*/
    private Timestamp createTime;
    /**更新者id*/
    private Integer updaterId;
    /**更新时间*/
    private Timestamp updateTime;
    // 盐值由系统随机生成
    /**盐值*/
    private String salt;

}
