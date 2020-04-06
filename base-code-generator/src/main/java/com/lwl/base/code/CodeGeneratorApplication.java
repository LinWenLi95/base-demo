package com.lwl.base.code;

import com.lwl.base.code.generator.freemarker.FreeMarkerCodeGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author P001
 */
@MapperScan(basePackages = "com.lwl.base.code.gen.dao")
@SpringBootApplication
public class CodeGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeGeneratorApplication.class, args);
        // 指定要生成的表名，可为空
//        String[] tableNames = {"sys_user"};
//        generate(tableNames);
    }

    /**
     * 生成代码
     * @param tableName 要生成的表名
     */
    public static void generate(String... tableName) {
        long start = System.currentTimeMillis();
        System.out.println("-------------------Code generate start!-------------------");
        // 生成对应代码
        FreeMarkerCodeGenerator codeGenerator = new FreeMarkerCodeGenerator(tableName);
        codeGenerator.generateAll();
        System.out.println("-------------------Code generate complete!-------------------");
        System.out.println(String.format("-------------------Execute %s ms!-------------------", System.currentTimeMillis() - start));
    }
}
