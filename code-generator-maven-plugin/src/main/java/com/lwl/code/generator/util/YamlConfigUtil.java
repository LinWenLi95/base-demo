package com.lwl.code.generator.util;

import com.alibaba.fastjson.JSONObject;
import com.lwl.code.generator.config.JdbcConfig;
import com.lwl.code.generator.config.CodeCopyRightConfig;
import com.lwl.code.generator.config.CodePackConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 配置获取类
 * @author P001
 */
public class YamlConfigUtil {

    private static CodeCopyRightConfig copyRightConfig;

    private static CodePackConfig codePackConfig;

    private static JdbcConfig jdbcConfig;
    /**外部项目优先根路径*/
    public static String priorityPath;
    /**外部项目优先yml配置文件路径*/
    private static String configFilePath;
    /**外部模板文件夹路径*/
    private static String templateDirPath;
    /**外部优先配置*/
    private static JSONObject priorityConfigs;
    /**内部默认配置*/
    private static JSONObject defaultConfigs;

    /**获取外部优先配置对象*/
    public static JSONObject getPriorityConfigs() {
        if (priorityConfigs == null) {
            priorityConfigs = getConfigs(configFilePath);
        }
        return priorityConfigs;
    }

    /**获取内部默认配置对象*/
    public static JSONObject getDefaultConfigs() {
        if (defaultConfigs == null) {
            defaultConfigs = getConfigs(null);
        }
        return defaultConfigs;
    }

    /**
     * 根据路径读取yml配置文件
     * @param configFilePath 配置文件路径(放空则读取默认配置)
     * @return JSONObject
     */
    private static JSONObject getConfigs(String configFilePath) {
        Yaml yaml = new Yaml();
        InputStream is = null;
        try {
            if (StringUtils.isEmpty(configFilePath)) {
                is = new ClassPathResource("application.yml").getInputStream();
            }else {
                is = new FileInputStream(configFilePath);
            }
            return yaml.loadAs(is, JSONObject.class);
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
        return null;
    }

    /**
     * 获取配置
     * @param key 指定key
     * @param clazz 类类型
     * @param <T> 泛型
     * @return T
     */
    public static <T> T getConfig(String key, Class<T> clazz) {
        T config = getConfig(getPriorityConfigs(), key, clazz);
        if (config == null) {
            config = getConfig(getDefaultConfigs(), key, clazz);
        }
        return config;
    }

    /**
     * 获取JSONObject中的指定value并转成指定的Java对象
     * @param configs JSONObject对象
     * @param key 指定key
     * @param clazz 类类型
     * @param <T> 泛型
     * @return T
     */
    public static <T> T getConfig(JSONObject configs, String key, Class<T> clazz) {
        if (configs != null && !StringUtils.isEmpty(key)) {
            String[] split = key.split("\\.");
            int length = split.length;
            JSONObject temp = null;
            for (int i = 0; i < length; i++) {
                if (i != length - 1) {
                    temp = configs.getJSONObject(split[i]);
                    if (temp == null) {
                        break;
                    }
                } else {
                    temp = temp == null ? configs : temp;
                    return temp.getObject(split[i], clazz);
                }
            }
        }
        return null;
    }

    /**
     * 设置路径
     * @param priorityPath 项目路径,未指定则默认为System.getProperty("user.dir")
     * @param configFilePath 指定配置文件路径,未指定则默认为src\main\resources\application.yml
     */
    public static void setPath(String priorityPath, String configFilePath) {
        // 指定项目根路径
        if (StringUtils.isEmpty(priorityPath)) {
            YamlConfigUtil.priorityPath = System.getProperty("user.dir");
        } else {
            YamlConfigUtil.priorityPath = priorityPath;
        }
        // 指定yml配置文件路径
        if (StringUtils.isEmpty(configFilePath)) {
            YamlConfigUtil.configFilePath = YamlConfigUtil.priorityPath + "\\src\\main\\resources\\application.yml";
        } else {
            // 规范配置字符
            configFilePath = configFilePath.replaceAll("/", "\\");
            if (configFilePath.charAt(0) == '\\') {
                YamlConfigUtil.configFilePath = YamlConfigUtil.priorityPath + configFilePath;
            } else {
                YamlConfigUtil.configFilePath = YamlConfigUtil.priorityPath + "\\" + configFilePath;
            }
        }
    }

    /**作者信息*/
    public static CodeCopyRightConfig getCopyRightConfig() {
        if (copyRightConfig == null) {
            copyRightConfig = getConfig("code-generator.copyright", CodeCopyRightConfig.class);
        }
        return copyRightConfig;
    }

    /**代码生成路径包信息*/
    public static CodePackConfig getCodePackConfig() {
        if (codePackConfig == null) {
            codePackConfig = getConfig("code-generator.file-path", CodePackConfig.class);
        }
        return codePackConfig;
    }

    /**数据库连接信息*/
    public static JdbcConfig getJdbcConfig() {
        if (jdbcConfig == null) {
            jdbcConfig = getConfig("code-generator.jdbc", JdbcConfig.class);
        }
        return jdbcConfig;
    }

    /**外部模板文件夹路径*/
    public static String getTemplateDirPath() {
        if (StringUtils.isEmpty(templateDirPath)) {
            templateDirPath = YamlConfigUtil.priorityPath + "\\src\\main\\resources\\templates";
        }
        return templateDirPath;
    }

    /**
     * 获取配置文件中要生成的表名
     * @return String[]
     */
    public static String[] getTableNames() {
        String tableNameStr = getConfig("code-generator.tables", String.class);
        return StringUtils.isEmpty(tableNameStr) ? null : tableNameStr.split(",");
    }
}
