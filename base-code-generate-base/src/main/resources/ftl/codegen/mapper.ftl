<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${codeConfig.daoPack}.${beanName?cap_first}Mapper">

    <!-- 添加 -->
    <insert id="insert" parameterType="${beanName}" useGeneratedKeys="true" keyProperty="uuid">
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

    <!-- 修改 -->
    <update id="update" parameterType="${beanName}">
        UPDATE ${tableName}
        <include refid="sqlUpdSet"/>
        <include refid="sqlUpdWhere"/>
    </update>

    <!-- 修改 -->
    <update id="updateWithMap">
        UPDATE ${tableName}
        <include refid="sqlUpdSet"/>
        <include refid="sqlUpdWhere"/>
    </update>

    <!-- 删除 -->
    <delete id="delete" parameterType="${beanName}">
        DELETE FROM ${tableName}
        WHERE uuid=${r'#{uuid}'}
    </delete>

    <!-- 查询（根据主键ID查询） -->
    <select id="getInfoByUuid" resultType="${beanName}">
        SELECT * FROM ${tableName}
        WHERE uuid=${r'#{uuid}'}
    </select>

    <!-- 查询（根据map查询） -->
    <select id="getInfoByMap" resultType="${beanName}">
        SELECT * FROM ${tableName}
        <include refid="sqlQueryWhere"/>
        ORDER BY create_time DESC LIMIT 1
    </select>

    <!-- 统计-->
    <select id="count" resultType="int">
        SELECT COUNT(uuid) FROM ${tableName}
        <include refid="sqlQueryWhere"/>
    </select>

    <!-- 获取list-->
    <select id="query" resultType="${beanName}">
        SELECT * FROM ${tableName}
        <include refid="sqlQueryWhere"/>
        ORDER BY create_time DESC
    </select>

    <!-- 分页查询-->
    <select id="queryPage" resultType="${beanName}">
        SELECT * FROM ${tableName}
        <include refid="sqlQueryWhere"/>
        ORDER BY create_time DESC
    </select>

    <!--获取列表的通用查询SQL-->
    <sql id="sqlQueryWhere">
        <where>
        <#list columns as column>
            <#if column.javaType="String">
            <if test="${column.javaField}!=null and ${column.javaField}!=''">and ${column.dbField}=#${r'{'}${column.javaField}${r'}'}</if>
            <#else>
            <if test="${column.javaField}!=null">and ${column.dbField}=#${r'{'}${column.javaField}${r'}'}</if>
            </#if>
        </#list>
        </where>
    </sql>

    <!--通用更新SQL-->
    <sql id="sqlUpdSet">
        <set>
        <#list columns as column>
            <#if column.javaField!="uuid">
                <#if column.javaType=="String">
                <if test="${column.javaField}!=null and ${column.javaField}!=''">${column.dbField}=#${r'{'}${column.javaField}${r'}'},</if>
                <#else>
                <if test="${column.javaField}!=null">${column.dbField}=#${r'{'}${column.javaField}${r'}'},</if>
                </#if>
            </#if>
        </#list>
        </set>
    </sql>

    <!--通用更新where-->
    <sql id="sqlUpdWhere">
        <where>
            <if test="uuid != null and uuid!=''">AND uuid = ${r'#{uuid}'}</if>
            <if test="lastUpdTime != null">and update_time = ${r'#{lastUpdTime}'}</if>
        </where>
    </sql>

</mapper>