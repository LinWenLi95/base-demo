package com.test;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import hehe.GeneratorApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis Plus Generator
 * @author linwenli
 * @date 2020/04/23
 */
@SpringBootTest(classes = GeneratorApplication.class)
public class CodeGenerator {

    @Value("${spring.datasource.driver-class-name}")
    private String driverName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Test
    public void generate() {
        //要生成代码的表名,多个表名使用英文逗号隔开
        String tableNames = "sys_user,sys_role";

        //通用包名
        String packageName = "com.baomidou.ant";
        //模块名（用于组成完整包名，自动拼接在包名后）
        String moduleName = "base.mybatis.plus";

        //作者
        String author = "LinWenLi";
        //生成类文件时，文件名去除指定表名前缀，放空则不去除
        //例如：表名为sys_user，生成类名：SysUser，设置去除"sys_"则生成：User
        String removeTablePrefix = "";
        //项目路径
        String projectPath = System.getProperty("user.dir");
        //代码输出路径
        String outputDir = projectPath + "/src/main/java";
        //mapper.xml输出路径
        String xmlOutputDir = projectPath + "/src/main/resources/mapper/";
        // 代码生成器
        AutoGenerator mpGenerator = new AutoGenerator();

        // 数据源配置（直接读取配置文件的数据源配置）
        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://47.106.198.29:3306/base_api?useUnicode=true&useSSL=false&characterEncoding=utf8");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("MysqL@951QwdC");
        dsc.setUrl(url);
        dsc.setDriverName(driverName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpGenerator.setDataSource(dsc);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir);
        gc.setAuthor(author);
        gc.setOpen(false);
        //实体属性 Swagger2 注解
//        gc.setSwagger2(true);
        mpGenerator.setGlobalConfig(gc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(packageName);
        mpGenerator.setPackageInfo(pc);

        /*配置模板*/
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        templateConfig.setEntity("templates/entity2.java");
//        templateConfig.setService();
//        templateConfig.setController();
//        templateConfig.setXml();
        mpGenerator.setTemplate(templateConfig);

        /*策略配置*/
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperEntityColumns("写于实体类的父类中的公共字段,没有就不用设置!");
//        strategy.setSuperEntityClass("实体类的父类,没有就不用设置!");
//        strategy.setSuperControllerClass("Controller的父类控制器,没有就不用设置!");
        //要生成代码的表名
        strategy.setInclude(tableNames.split(","));
        //作用未知
        strategy.setControllerMappingHyphenStyle(true);
        //生成类时文件名去除指定表前缀
        if (StringUtils.isNotBlank(removeTablePrefix)) {
            strategy.setTablePrefix(removeTablePrefix);
        }
        mpGenerator.setStrategy(strategy);

        /*额外生成*mapper.xml配置*/
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return xmlOutputDir + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpGenerator.setCfg(cfg);

        mpGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        mpGenerator.execute();
    }

}
