package com.lwl.code.generator;

import com.lwl.code.generator.freemarker.FreeMarkerCodeGenerator;
import com.lwl.code.generator.util.YamlConfigUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


/**
 * 示例Mojo
 * @author LinWenLi
 */
@Mojo(name = "generate")
public class CodeGenerateMojo extends AbstractMojo {

    /**项目路径,未指定则默认为System.getProperty("user.dir")*/
    @Parameter(property = "path")
    private String path;
    /**指定配置文件,未指定则默认为src\main\resources\application.yml*/
    @Parameter(property = "configFilePath")
    private String configFilePath;

    @Override
    public void execute() throws MojoExecutionException {
        // 设置优先读取配置文件的相关路径
        YamlConfigUtil.setPath(path, configFilePath);
        String[] tableNames = YamlConfigUtil.getTableNames();
        long start = System.currentTimeMillis();
        getLog().info("-------------------Code generate start!-------------------");
        FreeMarkerCodeGenerator codeGenerator = new FreeMarkerCodeGenerator(tableNames);
        codeGenerator.generateAll();
        getLog().info("-------------------Code generate complete!-------------------");
        getLog().info(String.format("-------------------Execute %s ms!-------------------", System.currentTimeMillis() - start));
    }

}
