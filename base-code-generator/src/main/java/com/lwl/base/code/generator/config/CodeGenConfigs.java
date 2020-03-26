package com.lwl.base.code.generator.config;

/**
 * 配置获取类
 * @author P001
 */
public class CodeGenConfigs {

    public static CodeCopyRightConfig copyRightConfig;

    public static CodePackConfig codePackConfig;

    public static JdbcConfig jdbcConfig;

    /**
     * 配置初始化
     * @param jdbcConfig jdbc配置
     * @param codePackConfig 代码文件生成包路径配置
     * @param copyRightConfig 代码版权配置
     */
    public static void init(JdbcConfig jdbcConfig,CodePackConfig codePackConfig,CodeCopyRightConfig copyRightConfig) {
        CodeGenConfigs.jdbcConfig = jdbcConfig;
        CodeGenConfigs.codePackConfig = codePackConfig;
        CodeGenConfigs.copyRightConfig = copyRightConfig;
    }
}
