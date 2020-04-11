package ${codeConfig.daoPack};

import com.lwl.base.code.common.mapper.BaseMapper;
import ${codeConfig.beanPack}.${beanName?cap_first};

/**
* ${beanDesc}数据库操作接口类
* @author ${copyRight.author}
* @date ${copyRight.since?string("yyyy/MM/dd")}
*/
public interface ${beanName?cap_first}Mapper<T extends ${beanName?cap_first}> extends BaseMapper<T> {

}