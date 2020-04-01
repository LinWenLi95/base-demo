package com.lwl.base.code.generator;

import com.lwl.base.code.generator.freemarker.FreeMarkerCodeGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author P001
 */
@SpringBootApplication
public class CodeGeneratorApplication {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("-------------------Code generate start!-------------------");
        // 指定要生成的表名，可为空
        String[] tableNames = {"sys_user"};
        // 生成对应代码
        FreeMarkerCodeGenerator codeGenerator = new FreeMarkerCodeGenerator(tableNames);
        codeGenerator.generateAll();
        System.out.println("-------------------Code generate complete!-------------------");
        System.out.println(String.format("-------------------Execute %s ms!-------------------", System.currentTimeMillis() - start));
    }
}
