package com.lwl.code.generator.freemarker;

/**
 * 代码生成器
 * @author lwl
 */
public interface CodeGenerator {

    /**生成实体类（领域对象）*/
    void generateBean();

    /**生成Dao*/
    void generateDao();

    /**生成Service*/
    void generateService();

    /**生成Controller*/
    void generateController();

    /**生成web资源页面及js*/
    void generateWebResource();

    /**生成所有代码*/
    default void generateAll(){
        generateBean();
        generateDao();
        generateService();
        generateController();
    };
}
