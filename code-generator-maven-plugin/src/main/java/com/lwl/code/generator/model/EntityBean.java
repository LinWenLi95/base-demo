package com.lwl.code.generator.model;

import com.lwl.code.generator.config.CodeCopyRightConfig;
import com.lwl.code.generator.util.FileUtil;
import com.lwl.code.generator.util.StringUtil;
import com.lwl.code.generator.util.YamlConfigUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 数据表转Java类的数据详情
 * @author Admin
 */
@Data
public class EntityBean {

    /**代码文件生成路径配置对象*/
    private CodeGenPackBean codeConfig;
    private CodeCopyRightConfig copyRight;
    private String tableName;
    private String beanName;
    private String beanDesc;
    /**需要显式导入的数据类型*/
    private List<String> imports;
    private List<ColumnBean> columns;

    public EntityBean(String tableName, String beanDesc) {
        this.beanName = StringUtil.underscoreToCamelCase(tableName);
        this.tableName = tableName;
        this.beanDesc = beanDesc;
        this.codeConfig = CodeGenPackBean.getInstance();
        this.copyRight = YamlConfigUtil.getCopyRightConfig();
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

    public String getBaseServiceOutput() {
        String dir = this.codeConfig.getServiceOutput();
        FileUtil.createIfNoExist(dir);
        return dir + "/" + "BaseService.java";
    }

    public String getServiceImplOutput() {
        String dir = this.codeConfig.getServiceImplOutput();
        FileUtil.createIfNoExist(dir);
        return dir + "/" + StringUtils.capitalize(this.beanName) + "ServiceImpl.java";
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
}
