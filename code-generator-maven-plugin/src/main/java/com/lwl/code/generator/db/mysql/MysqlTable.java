package com.lwl.code.generator.db.mysql;

import com.lwl.code.generator.model.EntityBean;
import lombok.Data;

import java.util.List;

/**
 * 数据库表信息对象（表名、表说明、列信息）
 * @author Admin
 */
@Data
public class MysqlTable implements Mysql57ToJavaConverter<EntityBean> {

    /**表名*/
    private String name;
    /**表说明*/
    private String comment;
    /**列信息对象列表*/
    private List<MysqlColumn> fields;

    public MysqlTable(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    @Override
    public EntityBean convert() {
        return new EntityBean(this.name, this.comment);
    }
}
