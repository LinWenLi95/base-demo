package ${codeConfig.beanPack};

import java.io.Serializable;
import lombok.Data;
<#list imports as imp>
<#if imp!="">
${imp}
</#if>
</#list>

/**
* ${beanDesc}
* @author ${copyRight.author}
* @date ${copyRight.since?string("yyyy/MM/dd")}
*/
@Data
public class ${beanName?cap_first} implements Serializable {

    private static final long serialVersionUID = 1L;

<#list columns as column>
    /**${column.comment}*/
    private ${column.javaType} ${column.javaField};
</#list>
}
