package com.lwl.base.spring.security.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* 系统用户 视图数据对象的时间属性必须以13位Long类型时间戳返回
* @author LinWenLi
* @date 2020/04/05
*/
@Data
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**id*/
    private Integer id;
    /**用户名*/
    private String username;
    // 密码 不传出
    /**密码，加密存储*/
    private String password;
    /**手机号*/
    private String telephone;
    /**邮箱*/
    private String email;
    /**状态 0禁用,1启动*/
    private Integer state;
    // 逻辑删除字段 不传出
    /**是否已删除*/
    private Boolean isDel;
    /**备注*/
    private String description;
    // 时间传时间戳// 逻辑删除字段 不传出// 密码 不传出
    /**创建者id*/
    private Integer creatorId;
    /**创建时间*/
    private Timestamp createTime;
    /**更新者id*/
    private Integer updaterId;
    /**更新时间*/
    private Timestamp updateTime;
    // 盐值不可传
    /**盐值*/
    private String salt;

    public Long getCreateTime() {
        return createTime == null ? null : createTime.getTime();
    }

    public Long getUpdateTime() {
        return updateTime == null ? null : updateTime.getTime();
    }
}
