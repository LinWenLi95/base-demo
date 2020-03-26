package ${codeConfig.daoPack};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${codeConfig.beanPack}.${beanName?cap_first};

/**
* ${beanDesc}数据库操作接口类
* @author ${copyRight.author}
* @since ${copyRight.since?string("yyyy/MM/dd")}
* @version ${copyRight.version}
*/
public interface ${beanName?cap_first}Mapper<T extends ${beanName?cap_first}> extends BaseMapper<T> {

}