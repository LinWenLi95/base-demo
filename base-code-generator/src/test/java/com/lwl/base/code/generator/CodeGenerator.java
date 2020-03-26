package com.lwl.base.code.generator;

import com.lwl.base.code.generator.config.CodeCopyRightConfig;
import com.lwl.base.code.generator.config.CodeGenConfigs;
import com.lwl.base.code.generator.config.CodePackConfig;
import com.lwl.base.code.generator.config.JdbcConfig;
import com.lwl.base.code.generator.freemarker.FreeMarkerCodeGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CodeGenerator {

    @Autowired
    CodeCopyRightConfig copyRightConfig;
    @Autowired
    CodePackConfig codePackConfig;
    @Autowired
    JdbcConfig jdbcConfig;

    @Test
    public void codeGenerate() {
        CodeGenConfigs.init(jdbcConfig, codePackConfig, copyRightConfig);
        String[] tableNames = {"sys_user"};
        FreeMarkerCodeGenerator codeGenerator = new FreeMarkerCodeGenerator(tableNames);
        codeGenerator.generateAll();
    }

}
