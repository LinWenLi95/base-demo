package com.lwl.base.code.generator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 代码生成人信息
 * @author Administrator
 */
@Data
@Component
@ConfigurationProperties(prefix = "code-generator.copyright")
public class CodeCopyRightConfig {

    /**作者*/
    private String author;
    /**版本*/
    private String version;
    /**生成时间*/
    private Date since;

    public CodeCopyRightConfig() {
        this.since = new Date();
    }

}
