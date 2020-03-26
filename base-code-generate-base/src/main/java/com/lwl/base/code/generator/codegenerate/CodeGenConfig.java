package com.lwl.base.code.generator.codegenerate;

import com.lwl.base.code.generator.codegenerate.db.JdbcConfig;
import com.lwl.base.code.generator.codegenerate.model.CodeCopyRight;
import com.lwl.base.code.generator.codegenerate.model.CodePackConfig;
import com.lwl.base.code.generator.codegenerate.util.YamlReader;

/**
 * 代码文件生成配置类（生成路径、文件版本信息等配置）
 */
public abstract class CodeGenConfig {
    protected static String PROPERTIES_FILE_NAME = "ftl/codegen";
    protected static String DRIVER_CLASS_KEY = "db.driverClass";
    protected static String DB_URL_KEY = "db.url";
    protected static String DB_USER_NAME_KEY = "db.userName";
    protected static String DB_PASSWORD_KEY = "db.password";
    protected static String CODE_PACK_BASE_KEY = "code.packBase";
    protected static String CODE_PACK_BEAN_KEY = "code.packBean";
    protected static String CODE_PACK_API_KEY = "code.packApi";
    protected static String CODE_PACK_CONTROLLER_KEY = "code.packController";
    protected static String CODE_PACK_SERVICE_KEY = "code.packService";
    protected static String CODE_PACK_DAO_KEY = "code.packDao";
    protected static String CODE_PACK_MAPPER_KEY = "code.packMapper";
    protected static String CODE_PACK_WEB_KEY = "code.packWeb";
    protected static String CODE_OUTPUT_BASE_KEY = "code.outputBase";
    public static final String BASE_PACKAGE = "com.andacx.";
    public static final String BEAN_PACKAGE = "entity";
    public static final String API_PACKAGE = "api";
    public static final String CONTROLLER_PACKAGE = "controller";
    public static final String SERVICE_PACKAGE = "service";
    public static final String DAO_PACKAGE = "dao";
    public static final String MAPPER_PACKAGE = "dao.mapper";
    public static final String WEB_PACKAGE = "web";
    public static final String OUTPUT_BASE = "d:/";
    protected static String COPYRIGHT_AUTHOR_KEY = "copyright.author";
    protected static String COPYRIGHT_VERSION_KEY = "copyright.version";
    public static final String BEAN_SUFFIX = "";
    public static final String API_SUFFIX = "Api";
    public static final String CONTROLLER_SUFFIX = "Controller";
    public static final String SERVICE_SUFFIX = "Service";
    public static final String DAO_SUFFIX = "Mapper";
    public static final String MAPPER_SUFFIX = "_mapper";
    private static final CodePackConfig CODE_PACK_CONFIG;
    private static final JdbcConfig JDBC_CONFIG;
    private static final CodeCopyRight CODE_COPYRIGHT;

    public CodeGenConfig() {
    }

    public static JdbcConfig getJdbcConfig() {
        return JDBC_CONFIG;
    }

    public static CodePackConfig getCodePackConfig() {
        return CODE_PACK_CONFIG;
    }

    public static CodeCopyRight getCodeCopyright() {
        return CODE_COPYRIGHT;
    }

    static {
        JDBC_CONFIG = new JdbcConfig();
        JDBC_CONFIG.setDriverClass(YamlReader.get(DRIVER_CLASS_KEY));
        JDBC_CONFIG.setUrl(YamlReader.get(DB_URL_KEY));
        JDBC_CONFIG.setUserName(YamlReader.get(DB_USER_NAME_KEY));
        JDBC_CONFIG.setPassword(YamlReader.get(DB_PASSWORD_KEY));
        CODE_PACK_CONFIG = new CodePackConfig();
        CODE_PACK_CONFIG.setPackBase(YamlReader.getOrDefault(CODE_PACK_BASE_KEY, "com.andacx."));
        CODE_PACK_CONFIG.setPackBean(YamlReader.getOrDefault(CODE_PACK_BEAN_KEY, "entity"));
        CODE_PACK_CONFIG.setPackApi(YamlReader.getOrDefault(CODE_PACK_API_KEY, "api"));
        CODE_PACK_CONFIG.setPackController(YamlReader.getOrDefault(CODE_PACK_CONTROLLER_KEY, "controller"));
        CODE_PACK_CONFIG.setPackService(YamlReader.getOrDefault(CODE_PACK_SERVICE_KEY, "service"));
        CODE_PACK_CONFIG.setPackDao(YamlReader.getOrDefault(CODE_PACK_DAO_KEY, "dao"));
        CODE_PACK_CONFIG.setPackMapper(YamlReader.getOrDefault(CODE_PACK_MAPPER_KEY, "dao.mapper"));
        CODE_PACK_CONFIG.setOutputBase(YamlReader.getOrDefault(CODE_OUTPUT_BASE_KEY, "d:/"));
        CODE_PACK_CONFIG.setPackWeb(YamlReader.getOrDefault(CODE_PACK_WEB_KEY, "web"));
        CODE_COPYRIGHT = new CodeCopyRight(YamlReader.getOrDefault(COPYRIGHT_AUTHOR_KEY, "zoro"), YamlReader.getOrDefault(COPYRIGHT_VERSION_KEY, "1.0"));
    }
}
