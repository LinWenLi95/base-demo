package com.lwl.base.code.generator.codegenerate.model;

import com.lwl.base.code.generator.codegenerate.CodeGenConfig;
import lombok.Data;

/**
 * @author
 * @since
 */
@Data
public class CodeConfigBean {
    /***/
    private String apiPack;
    /***/
    private String controllerPack;
    /***/
    private String beanPack;
    /***/
    private String servicePack;
    /***/
    private String daoPack;
    /***/
    private String mapperPack;
    /***/
    private String webPack;
    /***/
    private static CodePackConfig codePackConfig = CodeGenConfig.getCodePackConfig();

    public CodeConfigBean() {
        String packBase = codePackConfig.getPackBase() + ".";
        this.apiPack = packBase + codePackConfig.getPackApi();
        this.controllerPack = packBase + codePackConfig.getPackController();
        this.beanPack = packBase + codePackConfig.getPackBean();
        this.servicePack = packBase + codePackConfig.getPackService();
        this.daoPack = packBase + codePackConfig.getPackDao();
        this.mapperPack = packBase + codePackConfig.getPackMapper();
        this.webPack = codePackConfig.getPackWeb();
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
     * 拼接成文件路径
     * @param pack
     * @return
     */
    private static String packOutput(String pack) {
        return codePackConfig.getOutputBase() + pack.replaceAll("\\.", "\\\\");
    }

}
