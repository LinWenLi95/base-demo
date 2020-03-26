package com.lwl.base.code.generator.codegenerate.freemarker;

import com.lwl.base.code.generator.codegenerate.CodeGenerator;
import com.lwl.base.code.generator.codegenerate.db.DbHandler;
import com.lwl.base.code.generator.codegenerate.db.mysql.Mysql57DbHandler;
import com.lwl.base.code.generator.codegenerate.model.EntityBean;

import java.util.Iterator;
import java.util.List;

public class FreeMarkerCodeGenerator implements CodeGenerator {

    private static final String API_FLT = "api.ftl";
    private static final String CONTROLLER_FLT = "controller.ftl";
    private static final String SERVICE_FLT = "service.ftl";
    private static final String DAO_FLT = "dao.ftl";
    private static final String MAPPER_FLT = "mapper.ftl";
    private static final String BEAN_FLT = "bean.ftl";
    private static final String LIST_FLT = "list.ftl";
    private static final String LIST_JS_FLT = "listJS.ftl";
    private static final String EDIT_FLT = "edit.ftl";
    private static final String EDIT_JS_FLT = "editJS.ftl";
    private List<EntityBean> entityBeans;

    public FreeMarkerCodeGenerator() {
        DbHandler mysqlHandler = new Mysql57DbHandler();
        this.entityBeans = mysqlHandler.allTables();
        mysqlHandler.close();
    }

    @Override
    public void generateBean() throws Exception {
        Iterator var1 = this.entityBeans.iterator();

        while(var1.hasNext()) {
            EntityBean entityBean = (EntityBean)var1.next();
            FreeMarker.process(FreeMarker.Temp.get("bean.ftl"), entityBean, entityBean.getBeanOutput());
        }

    }

    @Override
    public void generateDao() throws Exception {
        Iterator var1 = this.entityBeans.iterator();

        while(var1.hasNext()) {
            EntityBean entityBean = (EntityBean)var1.next();
            FreeMarker.process(FreeMarker.Temp.get("dao.ftl"), entityBean, entityBean.getDaoOutput());
            FreeMarker.process(FreeMarker.Temp.get("mapper.ftl"), entityBean, entityBean.getMapperOutput());
        }

    }

    @Override
    public void generateService() throws Exception {
        Iterator var1 = this.entityBeans.iterator();

        while(var1.hasNext()) {
            EntityBean entityBean = (EntityBean)var1.next();
            FreeMarker.process(FreeMarker.Temp.get("service.ftl"), entityBean, entityBean.getServiceOutput());
        }

    }

    @Override
    public void generateApi() throws Exception {
        Iterator var1 = this.entityBeans.iterator();

        while(var1.hasNext()) {
            EntityBean entityBean = (EntityBean)var1.next();
            FreeMarker.process(FreeMarker.Temp.get("api.ftl"), entityBean, entityBean.getApiOutput());
            FreeMarker.process(FreeMarker.Temp.get("controller.ftl"), entityBean, entityBean.getControllerOutput());
        }

    }

    @Override
    public void generateWebResource() throws Exception {
        Iterator var1 = this.entityBeans.iterator();

        while(var1.hasNext()) {
            EntityBean entityBean = (EntityBean)var1.next();
            FreeMarker.process(FreeMarker.Temp.get("list.ftl"), entityBean, entityBean.getWebOutput("list.jsp"));
            FreeMarker.process(FreeMarker.Temp.get("listJS.ftl"), entityBean, entityBean.getWebOutput("list.js"));
            FreeMarker.process(FreeMarker.Temp.get("edit.ftl"), entityBean, entityBean.getWebOutput("edit.jsp"));
            FreeMarker.process(FreeMarker.Temp.get("editJS.ftl"), entityBean, entityBean.getWebOutput("edit.js"));
        }

    }
}
