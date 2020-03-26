package com.lwl.base.code.generator;

import com.lwl.base.code.generator.config.CodeCopyRightConfig;
import com.lwl.base.code.generator.config.CodeGenConfigs;
import com.lwl.base.code.generator.config.CodePackConfig;
import com.lwl.base.code.generator.config.JdbcConfig;
import com.lwl.base.code.generator.freemarker.FreeMarkerCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
        SpringApplication.run(CodeGeneratorApplication.class, args);
    }

    /**
     * 项目启动后自动运行run方法
     */
    @Component
    public static class CommandLineRunnerImpl implements CommandLineRunner {

        @Override
        public void run(String... args) {
            codeGenerate();
            // 使用Error直接中止应用
            throw new Error();
        }

        @Autowired
        private CodeCopyRightConfig copyRightConfig;
        @Autowired
        private CodePackConfig codePackConfig;
        @Autowired
        private JdbcConfig jdbcConfig;

        /**
         * 代码生成
         */
        private void codeGenerate() {
            long start = System.currentTimeMillis();
            System.out.println("-------------------Code generate start!-------------------");
            // 代码生成逻辑
            CodeGenConfigs.init(jdbcConfig, codePackConfig, copyRightConfig);
            // 指定要生成的表名，可为空
            String[] tableNames = {"sys_user"};
            FreeMarkerCodeGenerator codeGenerator = new FreeMarkerCodeGenerator(tableNames);
            // 生成对应代码
            codeGenerator.generateAll();
            long end = System.currentTimeMillis();
            System.out.println(String.format("-------------------Code generate complete!Execute %s ms-------------------", end - start));
        }
    }
}
