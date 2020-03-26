package com.lwl.base.code.generator.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

/**
 * FreeMarker资源加载类
 *
 * @author Admin
 */
public class FreeMarkerResourceLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreeMarkerResourceLoader.class);

    /**
     * FreeMarker配置对象
     */
    private static Configuration configuration;

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_29);
        configuration.setClassForTemplateLoading(FreeMarkerResourceLoader.class, "/templates");
        configuration.setDefaultEncoding("utf-8");
    }

    /**
     * 整合数据与模板并输出文件
     * @param templateName 模板文件名
     * @param data         数据对象
     * @param outFileName  生成文件路径
     */
    public static void process(String templateName, Object data, String outFileName) {
        Template template;
        Writer out = null;
        try {
            // 模板获取
            template = configuration.getTemplate(templateName, "utf-8");
            out = new FileWriterWithEncoding(outFileName, "utf-8");
            // 模板/数据整合输出文件
            template.process(data, out);
            LOGGER.info(String.format("Code file %s generated!", outFileName));
        } catch (IOException | TemplateException e) {
            LOGGER.error("FreeMaker process err:" + e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException var14) {
                    LOGGER.error("close writer err:" + var14.getMessage());
                }
            }
        }
    }
}
