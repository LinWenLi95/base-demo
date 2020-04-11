package com.lwl.code.generator.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author P001
 */
@Slf4j
public class FileUtil {

    /**
     * 目录不存在则创建
     * @param path 路径
     */
    public static void createIfNoExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (mkdirs) {
                log.info(String.format("目录:%s 生成成功", path));
            }
        }
    }
}
