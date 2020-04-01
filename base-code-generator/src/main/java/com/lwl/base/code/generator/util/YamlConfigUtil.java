package com.lwl.base.code.generator.util;

import com.alibaba.fastjson.JSONObject;
import com.lwl.base.code.generator.config.CodeCopyRightConfig;
import com.lwl.base.code.generator.config.CodePackConfig;
import com.lwl.base.code.generator.config.JdbcConfig;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

/**
 * 配置获取类
 * @author P001
 */
public class YamlConfigUtil {

    public static CodeCopyRightConfig copyRightConfig;

    public static CodePackConfig codePackConfig;

    public static JdbcConfig jdbcConfig;

    static {
        Yaml yaml = new Yaml();
        JSONObject config = null;
        InputStream is = null;
        try {
            is = new ClassPathResource("application.yml").getInputStream();
            config = yaml.loadAs(is, JSONObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (config != null) {
            JSONObject codeGenerator = config.getJSONObject("code-generator");
            jdbcConfig = codeGenerator.getObject("db", JdbcConfig.class);
            codePackConfig = codeGenerator.getObject("generate", CodePackConfig.class);
            copyRightConfig = codeGenerator.getObject("copyright", CodeCopyRightConfig.class);
        }
    }
}
