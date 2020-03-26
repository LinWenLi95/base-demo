package com.lwl.base.code.generator.codegenerate;

/**
 * 逆向工程生成代码
 * @author lwl
 */
public interface CodeGenerator {

    /**生成实体类（领域对象）*/
    void generateBean() throws Exception;

    /**生成Dao*/
    void generateDao() throws Exception;

    /**生成Service*/
    void generateService() throws Exception;

    /**生成ApiController*/
    void generateApi() throws Exception;

    /**生成web资源页面及js*/
    void generateWebResource() throws Exception;

    /**生成所有代码*/
    default void generateAll() throws Exception{
        generateBean();
        generateDao();
        generateService();
        generateApi();
        generateWebResource();
    };
}
