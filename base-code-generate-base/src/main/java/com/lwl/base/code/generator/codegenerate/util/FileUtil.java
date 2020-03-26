package com.lwl.base.code.generator.codegenerate.util;

import java.io.File;

public class FileUtil {
    public FileUtil() {
    }

    public static String createIfNoExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        return path;
    }
}
