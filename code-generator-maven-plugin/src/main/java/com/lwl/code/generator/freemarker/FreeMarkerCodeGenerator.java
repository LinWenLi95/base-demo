package com.lwl.code.generator.freemarker;

import com.lwl.code.generator.db.DbHandler;
import com.lwl.code.generator.db.mysql.Mysql57DbHandler;
import com.lwl.code.generator.model.EntityBean;

import java.util.List;

/**
 * 代码生成器实现类
 * @author Admin
 */
public class FreeMarkerCodeGenerator implements CodeGenerator {

    /**
     * 数据库表信息/列信息转化的Java对象列表
     */
    private List<EntityBean> entityBeans;

    /**
     * 代码生成器
     * @param tableName 表名 放空则生成所有表的代码
     */
    public FreeMarkerCodeGenerator(String... tableName) {
        DbHandler mysqlHandler = new Mysql57DbHandler();
        this.entityBeans = mysqlHandler.tableListByName(tableName);
        mysqlHandler.close();
    }

    @Override
    public void generateBean() {
        for (EntityBean entityBean : this.entityBeans) {
            FreeMarkerResourceLoader.process("bean.ftl", entityBean, entityBean.getBeanOutput());
        }
    }

    @Override
    public void generateDao() {
        for (EntityBean entityBean : this.entityBeans) {
            FreeMarkerResourceLoader.process("dao.ftl", entityBean, entityBean.getDaoOutput());
            FreeMarkerResourceLoader.process("mapper.ftl", entityBean, entityBean.getMapperOutput());
        }
    }

    @Override
    public void generateService() {
        for (EntityBean entityBean : this.entityBeans) {
            FreeMarkerResourceLoader.process("service.ftl", entityBean, entityBean.getServiceOutput());
            FreeMarkerResourceLoader.process("serviceImpl.ftl", entityBean, entityBean.getServiceImplOutput());
        }
    }

    @Override
    public void generateController() {
        for (EntityBean entityBean : this.entityBeans) {
            FreeMarkerResourceLoader.process("controller.ftl", entityBean, entityBean.getControllerOutput());
        }
    }

    @Override
    public void generateWebResource() {
        for (EntityBean entityBean : this.entityBeans) {
            FreeMarkerResourceLoader.process("list.ftl", entityBean, entityBean.getWebOutput("list.jsp"));
            FreeMarkerResourceLoader.process("listJS.ftl", entityBean, entityBean.getWebOutput("list.js"));
            FreeMarkerResourceLoader.process("edit.ftl", entityBean, entityBean.getWebOutput("edit.jsp"));
            FreeMarkerResourceLoader.process("editJS.ftl", entityBean, entityBean.getWebOutput("edit.js"));
        }
    }
}
