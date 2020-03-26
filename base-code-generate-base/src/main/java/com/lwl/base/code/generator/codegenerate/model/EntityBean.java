package com.lwl.base.code.generator.codegenerate.model;

import com.lwl.base.code.generator.codegenerate.CodeGenConfig;
import com.lwl.base.code.generator.codegenerate.util.FileUtil;
import com.lwl.base.code.generator.codegenerate.util.StringUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 实体类信息对象
 * @author Admin
 */
@Data
public class EntityBean {

    private CodeConfigBean codeConfig;
    private CodeCopyRight copyRight;
    private String tableName;
    private String beanName;
    private String beanDesc;
    private List<String> imps;
    private List<ColumnBean> columns;

    public EntityBean(String tableName, String beanDesc) {
        this.tableName = tableName;
        this.beanDesc = beanDesc;
        this.codeConfig = new CodeConfigBean();
        this.beanName = StringUtil.underscoreToCamelCase(tableName);
        this.copyRight = CodeGenConfig.getCodeCopyright();
    }

    public String getApiOutput() {
        String dir = this.codeConfig.getApiOutput();
        FileUtil.createIfNoExist(dir);
        return dir + "/" + StringUtils.capitalize(this.beanName) + "Api.java";
    }

    public String getControllerOutput() {
        String dir = this.codeConfig.getControllerOutput();
        FileUtil.createIfNoExist(dir);
        return dir + "/" + StringUtils.capitalize(this.beanName) + "Controller.java";
    }

    public String getBeanOutput() {
        String dir = this.codeConfig.getBeanOutput();
        FileUtil.createIfNoExist(dir);
        return dir + "/" + StringUtils.capitalize(this.beanName) + ".java";
    }

    public String getServiceOutput() {
        String dir = this.codeConfig.getServiceOutput();
        FileUtil.createIfNoExist(dir);
        return dir + "/" + StringUtils.capitalize(this.beanName) + "Service.java";
    }

    public String getDaoOutput() {
        String dir = this.codeConfig.getDaoOutput();
        FileUtil.createIfNoExist(dir);
        return dir + "/" + StringUtils.capitalize(this.beanName) + "Mapper.java";
    }

    public String getMapperOutput() {
        String dir = this.codeConfig.getMapperOutput();
        FileUtil.createIfNoExist(dir);
        return dir + "/" + StringUtils.capitalize(this.beanName) + "_Mapper.xml";
    }

    public String getWebOutput(String fileName) {
        String dir = this.codeConfig.getWebOutput() + "/" + this.beanName;
        FileUtil.createIfNoExist(dir);
        return dir + "/" + fileName;
    }

    @Override
    public String toString() {
        return "EntityBean{codeConfig=" + this.codeConfig + ", tableName='" + this.tableName + '\'' + ", beanName='" + this.beanName + '\'' + ", beanDesc='" + this.beanDesc + '\'' + ", columns=" + this.columns + '}';
    }
}
