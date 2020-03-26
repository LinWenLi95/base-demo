package com.lwl.base.code.generator.codegenerate.model;

import com.lwl.base.code.generator.codegenerate.util.StringUtil;
import lombok.Data;

@Data
public class ColumnBean {
    private String dbField;
    private String javaField;
    private String javaType;
    private String imp;
    private String comment;

    public ColumnBean(String dbField, String javaType, String imp, String comment) {
        this.dbField = dbField;
        this.javaField = StringUtil.underscoreToCamelCase(dbField);
        this.javaType = javaType;
        this.imp = imp;
        this.comment = comment;


    }

    @Override
    public String toString() {
        return "ColumnBean{dbField='" + this.dbField + '\'' + ", javaField='" + this.javaField + '\'' + ", javaType='" + this.javaType + '\'' + ", imp='" + this.imp + '\'' + ", comment='" + this.comment + '\'' + '}';
    }


}
