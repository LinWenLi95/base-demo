package com.lwl.base.code.generator.codegenerate.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FreeMarker {
    public static final String DEFAULT_ENCODING = "utf-8";
    private static final Logger LOGGER = LoggerFactory.getLogger(FreeMarker.class);
    public static final String FTL_BASE_PATH = "/ftl/codegen";
    static Configuration configuration;

    public FreeMarker() {
    }

    public static void process(Template template, Object data, String outFileName) {
        FileWriterWithEncoding out = null;

        try {
            out = new FileWriterWithEncoding(outFileName, "utf-8");
            template.process(data, out);
        } catch (IOException | TemplateException var15) {
            LOGGER.error("FreeMaker process err:" + var15.getMessage());
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

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_23);
//        try {
//            configuration.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\ftl\\codegen"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        configuration.setClassForTemplateLoading(FreeMarker.class, "/ftl/codegen");
        configuration.setDefaultEncoding("utf-8");
    }

    public static class Temp {
        public Temp() {
        }

        public static final Template get(String name) throws IOException {
            return FreeMarker.configuration.getTemplate(name, "utf-8");
        }
    }
}
