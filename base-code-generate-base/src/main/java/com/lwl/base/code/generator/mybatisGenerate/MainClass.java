package com.lwl.base.code.generator.mybatisGenerate;

import com.lwl.base.code.generator.codegenerate.CodeGenerator;
import com.lwl.base.code.generator.codegenerate.freemarker.FreeMarkerCodeGenerator;

/**
 * mysql逆向工程
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        // 可能出现的问题：表字段类型对不上，比如表内为float，无法匹配到。
        CodeGenerator codeGenerator = new FreeMarkerCodeGenerator();
        codeGenerator.generateBean();
//        codeGenerator.generateDao();
//        codeGenerator.generateService();
    }
}