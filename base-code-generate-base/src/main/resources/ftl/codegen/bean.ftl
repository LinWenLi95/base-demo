package ${codeConfig.beanPack};

import java.io.Serializable;
import lombok.Data;
<#list imps as imp>
<#if imp!="">
${imp}
</#if>
</#list>

/**
* ${beanDesc}
* @author ${copyRight.author}
* @since ${copyRight.since?string("yyyy/MM/dd")}
*/
@Data
public class ${beanName?cap_first} implements Serializable {

    private static final long serialVersionUID = 1L;

<#list columns as column>
    /**${column.comment}*/
    private ${column.javaType} ${column.javaField};
</#list>
}
