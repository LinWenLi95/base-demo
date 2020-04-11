package com.lwl.code.generator.freemarker;

import com.lwl.code.generator.util.YamlConfigUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * FreeMarker资源加载类
 *
 * @author Admin
 */
public class FreeMarkerResourceLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreeMarkerResourceLoader.class);

    /**FreeMarker配置对象(加载内部模板)*/
    private static Configuration configuration;
    /**FreeMarker配置对象(加载外部模板)*/
    private static Configuration priorityConfiguration;

    /**获取外部模板配置对象*/
    private static Configuration getPriorityConfiguration() throws IOException {

        if (priorityConfiguration == null) {
            // 初始化外部配置模板对象
            priorityConfiguration = new Configuration(Configuration.VERSION_2_3_29);
            priorityConfiguration.setDirectoryForTemplateLoading(new File(YamlConfigUtil.getTemplateDirPath()));
//            priorityConfiguration.setClassForTemplateLoading(FreeMarkerResourceLoader.class, YamlConfigUtil.getTemplateDirPath());
            priorityConfiguration.setDefaultEncoding("utf-8");
        }
        return priorityConfiguration;
    }

    /**获取内部模板配置对象*/
    private static Configuration getConfiguration() {
        if (configuration == null) {
            // 初始化内部模板配置对象
            configuration = new Configuration(Configuration.VERSION_2_3_29);
            configuration.setClassForTemplateLoading(FreeMarkerResourceLoader.class, "/templates");
            configuration.setDefaultEncoding("utf-8");
        }
        return configuration;
    }

    /**
     * 整合数据与模板并输出文件
     * @param templateName 模板文件名
     * @param data         数据对象
     * @param outFileName  生成文件路径
     */
    public static void process(String templateName, Object data, String outFileName) {
        Template template = null;
        Writer out = null;
        try {
            // 模板获取,优先从外部模板配置对象获取
            File file = new File(YamlConfigUtil.getTemplateDirPath() + "\\" + templateName);
            if (file.exists()) {
                template = getPriorityConfiguration().getTemplate(templateName, "utf-8");
            }
            if (template == null) {
                template = getConfiguration().getTemplate(templateName, "utf-8");
            }
            if (template == null) {
                LOGGER.error("获取模板:'{}'失败", templateName);
                return;
            }
            out = new FileWriterWithEncoding(outFileName, "utf-8");
            // 模板/数据整合输出文件
            template.process(data, out);
            LOGGER.info(String.format("Code file generated!FilePath: %s ", outFileName));
        } catch (IOException | TemplateException e) {
            LOGGER.error("FreeMaker process err:" + e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("close writer err:" + e.getMessage());
                }
            }
        }
    }
}
