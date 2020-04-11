package com.lwl.code.generator.db.mysql;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * MySQL数据类型与Java数据类型的映射枚举类
 * @author Admin
 */
public enum Mysql57Mapping {
    /*mysql数据类型转换Java数据类型*/
    CHAR("char", String.class, ""),
    DATE("date", Date.class, "import java.util.Date;"),
    TIMESTAMP("timestamp", Timestamp.class, "import java.sql.Timestamp;"),
    INT("int", Integer.class, ""),
    BIGINT("bigint", Long.class, ""),
    TEXT("text", String.class, ""),
    BIT("bit", Boolean.class, ""),
    DECIMAL("decimal", BigDecimal.class, "import java.math.BigDecimal;"),
    BLOB("blob", byte[].class, ""),
    DOUBLE("double", Double.class, "");

    /**
     * mysql数据类型
     */
    private String mysqlType;
    /**
     * Java数据类型
     */
    private String javaType;
    /**
     * Java数据类型的Class类型
     */
    private Class javaClass;
    /**
     * Java包装类的包路径导入语句（基本数据类型无需添加）
     */
    private String typeImport;

    private Mysql57Mapping(String mysqlType, Class javaClass, String typeImport) {
        this.mysqlType = mysqlType;
        this.javaType = javaClass.getSimpleName();
        this.javaClass = javaClass;
        this.typeImport = typeImport;
    }

    public String getMysqlType() {
        return this.mysqlType;
    }

    public String getJavaType() {
        return this.javaType;
    }

    public Class getJavaClass() {
        return this.javaClass;
    }

    public String getTypeImport() {
        return this.typeImport;
    }

    /**
     * MySQL数据类型转Java数据类型
     * @param mysqlType MySQL数据类型
     */
    public static Mysql57Mapping toJavaType(String mysqlType) {
        if (null != mysqlType && !"".equals(mysqlType)) {
            Mysql57Mapping[] mappings = values();
            for (Mysql57Mapping mysqlType2Java : mappings) {
                if (mysqlType.contains(mysqlType2Java.getMysqlType())) {
                    return mysqlType2Java;
                }
            }
        }
        return null;
    }
}
