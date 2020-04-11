package com.lwl.code.generator.model;

import com.lwl.code.generator.util.YamlConfigUtil;
import com.lwl.code.generator.config.CodePackConfig;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 各项代码生成包路径实体
 * @author P001
 */
@Data
public class CodeGenPackBean {
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
    private CodePackConfig codePackConfig = YamlConfigUtil.getCodePackConfig();

    private static CodeGenPackBean codeGenPackBean;

    public CodeGenPackBean() {
        String packBase = codePackConfig.getPackBase() + ".";
        this.controllerPack = packBase + codePackConfig.getPackController();
        this.servicePack = packBase + codePackConfig.getPackService();
        this.serviceIpmlPack = servicePack + ".impl";
        this.daoPack = packBase + codePackConfig.getPackDao();
        if (!codePackConfig.getPackMapper().startsWith("classpath:")) {
            this.mapperPack = packBase + codePackConfig.getPackMapper();
        }else {
            this.mapperPack = codePackConfig.getPackMapper();
        }
        this.beanPack = packBase + codePackConfig.getPackBean();
        this.webPack = codePackConfig.getPackWeb();
    }

    public static CodeGenPackBean getInstance() {
        if (codeGenPackBean == null) {
            codeGenPackBean = new CodeGenPackBean();
        }
        return codeGenPackBean;
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
        if (pack.startsWith("classpath:")) {
            pack = pack.replace("classpath:", "");
            return String.format("%s\\src\\main\\resources\\%s", YamlConfigUtil.priorityPath, pack.replaceAll("\\.", "\\\\"));
        }else if (StringUtils.isEmpty(codePackConfig.getOutputBase())) {
            return String.format("%s\\src\\main\\java\\%s", YamlConfigUtil.priorityPath, pack.replaceAll("\\.", "\\\\"));
        }
        return codePackConfig.getOutputBase() + "\\" + pack.replaceAll("\\.", "\\\\");
    }

}
