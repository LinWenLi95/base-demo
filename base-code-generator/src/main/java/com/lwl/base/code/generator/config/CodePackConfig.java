package com.lwl.base.code.generator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 代码文件生成路径配置
 * @author Admin
 */
@Data
@Component
@ConfigurationProperties(prefix = "code-generator.generate")
public class CodePackConfig {

    /**代码文件根路径 例：com.lwl.base.demo*/
    private String packBase;
    /**实体代码路径 例：pojo*/
    private String packBean;
    /**api代码路径 例：*/
    private String packApi;
    /**controller代码文件路径 例：controller*/
    private String packController;
    /**service代码文件路径 例：service*/
    private String packService;
    /**dao接口代码文件路径 例：dao*/
    private String packDao;
    /**mapper代码文件路径 例：mapper*/
    private String packMapper;
    /**web代码文件路径 例：com/lwl/web*/
    private String packWeb;
    /**文件保存路径 例：D:/base-demo/*/
    private String outputBase;
}
