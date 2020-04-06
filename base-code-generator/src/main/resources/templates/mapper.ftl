<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${codeConfig.daoPack}.${beanName?cap_first}Mapper">

    <!-- 查询单条记录（主键ID查询） -->
    <select id="selectById" resultType="${codeConfig.beanPack}.${beanName?cap_first}">
        SELECT
        <if test="columns==null ">
            <include refid="allColumns"/>
        </if>
        <if test="columns!=null ">
            <foreach item="item" index="index" collection="columns" separator=",">
                ${r'${item}'}
            </foreach>
        </if>
        FROM ${tableName}
        WHERE id=${r'#{id}'}
        LIMIT 1
    </select>

    <!-- 查询单条记录（map条件查询） -->
    <select id="selectByMap" resultType="${codeConfig.beanPack}.${beanName?cap_first}">
        SELECT
        <if test="columns==null ">
            <include refid="allColumns"/>
        </if>
        <if test="columns!=null ">
            <foreach item="item" index="index" collection="columns" separator=",">
                ${r'${item}'}
            </foreach>
        </if>
        FROM ${tableName}
        <include refid="sqlQueryWhere"/>
        ORDER BY create_time DESC
        LIMIT 1
    </select>

    <!-- 分页查询（map条件查询）-->
    <select id="selectPage" resultType="${codeConfig.beanPack}.${beanName?cap_first}">
        SELECT
        <if test="columns==null ">
            <include refid="allColumns"/>
        </if>
        <if test="columns!=null ">
            <foreach item="item" index="index" collection="columns" separator=",">
                ${r'${item}'}
            </foreach>
        </if>
        FROM ${tableName}
        <include refid="sqlQueryWhere"/>
        <if test="order_by==null or order_by=='' or sort==null or sort==''">
            ORDER BY create_time DESC
        </if>
        <if test="order_by!=null and order_by!='' and sort!=null and sort!=''">
            ORDER BY ${r'${order_by} ${sort}'}
        </if>
        <if test="offset==null and limit!=null">
            LIMIT ${r'${limit}'}
        </if>
        <if test="offset!=null and limit!=null">
            LIMIT ${r'#{offset},${limit}'}
        </if>
    </select>

    <!-- 统计数量（map条件查询）-->
    <select id="count" resultType="int">
        SELECT COUNT(id)
        FROM ${tableName}
        <include refid="sqlQueryWhere"/>
        LIMIT 1
    </select>

    <!-- 添加 单条记录 -->
    <insert id="insert" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO ${tableName} (
        <#list columns as column>
            ${column.dbField}<#if column_has_next>,</#if>
        </#list>
        )
        VALUES (
        <#list columns as column>
            #${r'{'}${column.javaField}${r'}'}<#if column_has_next>,</#if>
        </#list>
        )
    </insert>

    <!-- 修改 单条记录 -->
    <update id="update" parameterType="${codeConfig.beanPack}.${beanName?cap_first}">
        UPDATE ${tableName}
        <include refid="sqlUpdSet"/>
        WHERE id=${r'#{id}'}
        LIMIT 1
    </update>

    <!-- 删除 单条记录 -->
    <delete id="delete" parameterType="${codeConfig.beanPack}.${beanName?cap_first}">
        DELETE FROM ${tableName}
        WHERE id=${r'#{id}'}
        LIMIT 1
    </delete>

    <!--通用查询SQL-->
    <sql id="sqlQueryWhere">
        <where>
            <#list columns as column>
            <if test="${column.javaField}!=null <#if column.javaType="String">and ${column.javaField}!=''</#if>">
                and ${column.dbField}=#${r'{'}${column.javaField}${r'}'}
            </if>
            </#list>
        </where>
    </sql>

    <!--通用更新SQL-->
    <sql id="sqlUpdSet">
        <set>
            <#list columns as column>
                <#if column.javaField!="id">
            <if test="${column.javaField}!=null <#if column.javaType=="String">and ${column.javaField}!=''</#if>">
                ${column.dbField}=#${r'{'}${column.javaField}${r'}'},
            </if>
                </#if>
            </#list>
        </set>
    </sql>

    <!--所有列名（用于替代select *中的*）-->
    <sql id="allColumns">
        <#list columns as column>
        ${column.dbField}<#if column_has_next>,</#if>
        </#list>
    </sql>
</mapper>