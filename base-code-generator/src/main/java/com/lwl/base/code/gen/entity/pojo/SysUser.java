package com.lwl.base.code.gen.entity.pojo;

import java.io.Serializable;
import lombok.Data;

import java.sql.Timestamp;

/**
* 系统 用户表
* @author LinWenLi
* @date 2020/04/05
*/
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**id*/
    private Integer id;
    /**用户名*/
    private String username;
    /**密码，加密存储*/
    private String password;
    /**手机号*/
    private String telephone;
    /**邮箱*/
    private String email;
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
    /**盐值*/
    private String salt;

}
