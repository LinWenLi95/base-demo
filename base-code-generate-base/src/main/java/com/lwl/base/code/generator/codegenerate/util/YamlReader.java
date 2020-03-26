package com.lwl.base.code.generator.codegenerate.util;

import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 读取代码生成配置
 * @author linwenli
 */
public class YamlReader {

    private static Map<String, Map<String, Object>> data = null;

    static {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        //读入文件
        try {
            inputStream = new FileInputStream("src/main/resources/codegen.yml");
            data = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取配置文件中key对应的值
     * @param key
     * @return
     */
    public static String get(String key) {
        if (data != null) {
            String[] keys = key.split("\\.");
            if (keys.length != 2) {
                return null;
            }
            Map<String, Object> db = data.get(keys[0]);
            if (db != null) {
                return db.get(keys[1]).toString();
            }
        }
        return null;
    }

    /**
     * 获取配置文件中key对应的值
     * @param key 键
     * @param defaultVal 当值为空时返回默认值
     * @return
     */
    public static String getOrDefault(String key, String defaultVal) {
        String val;
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(val = get(key))) {
            if (StringUtils.isEmpty(defaultVal)) {
                return null;
            }
            return defaultVal;
        }
        return val;
    }
}
