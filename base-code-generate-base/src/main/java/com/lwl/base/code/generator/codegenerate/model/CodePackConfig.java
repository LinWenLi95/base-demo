package com.lwl.base.code.generator.codegenerate.model;

import lombok.Data;

/**
 * 代码文件生成路径配置
 */
@Data
public class CodePackConfig {

    /**代码文件根路径*/
    private String packBase;
    /**实体代码文件路径*/
    private String packBean;
    /**api代码文件路径*/
    private String packApi;
    /**controller代码文件路径*/
    private String packController;
    /**service代码文件路径*/
    private String packService;
    private String packDao;
    /**mapper代码文件路径*/
    private String packMapper;
    /**web代码文件路径*/
    private String packWeb;
    /**文件保存路径*/
    private String outputBase;
}
