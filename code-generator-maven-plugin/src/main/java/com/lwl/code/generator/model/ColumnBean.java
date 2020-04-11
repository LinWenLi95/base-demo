package com.lwl.code.generator.model;

import com.lwl.code.generator.util.StringUtil;
import lombok.Data;

/**
 * 数据列转Java属性的数据详情
 * @author Admin
 */
@Data
public class ColumnBean {

    /**数据列名*/
    private String dbField;
    /**Java属性名*/
    private String javaField;
    /**数据列对应的Java数据类型*/
    private String javaType;
    /**Java数据类型显式导入（基本类型无需导入，可放空）*/
    private String typeImport;
    /**数据列解释*/
    private String comment;

    /**
     *
     * @param dbField 数据列名
     * @param javaType 数据列对应的Java数据类型
     * @param typeImport Java数据类型显式导入（基本类型无需导入，可放空）
     * @param comment 数据列解释
     */
    public ColumnBean(String dbField, String javaType, String typeImport, String comment) {
        this.dbField = dbField;
        this.javaField = StringUtil.underscoreToCamelCase(dbField);
        this.javaType = javaType;
        this.typeImport = typeImport;
        this.comment = comment;
    }

}
