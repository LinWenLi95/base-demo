package com.lwl.base.code.generator.model;

import com.lwl.base.code.generator.config.CodeGenConfigs;
import com.lwl.base.code.generator.config.CodePackConfig;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 各项代码生成包路径实体
 * @author P001
 */
@Data
public class CodeGenPackBean {
    /***/
    private String apiPack;
    /***/
    private String controllerPack;
    /***/
    private String beanPack;
    /***/
    private String servicePack;
    /***/
    private String serviceIpmlPack;
    /***/
    private String daoPack;
    /***/
    private String mapperPack;
    /***/
    private String webPack;
    /***/
    private CodePackConfig codePackConfig = CodeGenConfigs.codePackConfig;

    private static CodeGenPackBean codeGenPackBean;

    public CodeGenPackBean() {
        String packBase = codePackConfig.getPackBase() + ".";
        this.apiPack = packBase + codePackConfig.getPackApi();
        this.controllerPack = packBase + codePackConfig.getPackController();
        this.beanPack = packBase + codePackConfig.getPackBean();
        this.servicePack = packBase + codePackConfig.getPackService();
        this.serviceIpmlPack = servicePack + ".impl";
        this.daoPack = packBase + codePackConfig.getPackDao();
        this.mapperPack = packBase + codePackConfig.getPackMapper();
        this.webPack = codePackConfig.getPackWeb();
    }

    public static CodeGenPackBean getInstance() {
        if (codeGenPackBean == null) {
            codeGenPackBean = new CodeGenPackBean();
        }
        return codeGenPackBean;
    }

    public String getApiOutput() {
        return packOutput(this.apiPack);
    }

    public String getControllerOutput() {
        return packOutput(this.controllerPack);
    }

    public String getBeanOutput() {
        return packOutput(this.beanPack);
    }

    public String getServiceOutput() {
        return packOutput(this.servicePack);
    }

    public String getServiceImplOutput() {
        return packOutput(this.serviceIpmlPack);
    }

    public String getDaoOutput() {
        return packOutput(this.daoPack);
    }

    public String getMapperOutput() {
        return packOutput(this.mapperPack);
    }

    public String getWebOutput() {
        return packOutput(this.webPack);
    }

    /**
     * 拼接成文件生成路径
     * @param pack 文件的包路径
     * @return 文件绝对路径
     */
    private String packOutput(String pack) {
        if (StringUtils.isEmpty(codePackConfig.getOutputBase())) {
            String projectName = "\\base-code-generator";
            String dir = System.getProperty("user.dir");
            dir = dir.contains(projectName) ? dir : dir + projectName;
            return String.format("%s\\src\\main\\java\\%s", dir, pack.replaceAll("\\.", "\\\\"));
        }
        return codePackConfig.getOutputBase() + pack.replaceAll("\\.", "\\\\");
    }

}
