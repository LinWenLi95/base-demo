package com.lwl.base.code.generator.util;

import java.io.File;

/**
 * @author P001
 */
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
                System.out.println(String.format("目录:%s 生成成功", path));
            }
        }
    }
}
