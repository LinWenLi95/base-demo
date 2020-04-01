package com.lwl.base.code.generator;

import com.lwl.base.code.generator.freemarker.FreeMarkerCodeGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

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
        FreeMarkerCodeGenerator codeGenerator = new FreeMarkerCodeGenerator(tableNames);
        // 生成对应代码
        codeGenerator.generateAll();
        long end = System.currentTimeMillis();
        System.out.println(String.format("-------------------Code generate complete!Execute %s ms-------------------", end - start));
    }
}
