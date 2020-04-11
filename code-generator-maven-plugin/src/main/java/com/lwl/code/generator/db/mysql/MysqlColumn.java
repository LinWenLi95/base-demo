package com.lwl.code.generator.db.mysql;

import com.lwl.code.generator.model.ColumnBean;
import lombok.Data;

/**
 * 数据库表的列信息对象
 * @author Admin
 */
@Data
public class MysqlColumn implements Mysql57ToJavaConverter<ColumnBean> {

    /**字段名*/
    private String field;
    /**字段类型*/
    private String type;
    /**是否可为空*/
    private Boolean nullable;
    /**字段备注*/
    private String comment;

    public MysqlColumn(String field, String type, Boolean nullable, String comment) {
        this.field = field;
        this.type = type;
        this.nullable = nullable;
        this.comment = comment;
    }

    @Override
    public ColumnBean convert() {
        Mysql57Mapping mysql57Mapping = Mysql57Mapping.toJavaType(this.type);
        return new ColumnBean(this.field, mysql57Mapping.getJavaType(), mysql57Mapping.getTypeImport(), this.comment);
    }
}
