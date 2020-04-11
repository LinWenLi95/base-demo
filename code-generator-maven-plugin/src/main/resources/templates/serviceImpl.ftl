package ${codeConfig.servicePack}.impl;

import com.lwl.base.code.common.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${codeConfig.beanPack}.${beanName?cap_first};
import ${codeConfig.daoPack}.${beanName?cap_first}Mapper;
import ${codeConfig.servicePack}.${beanName?cap_first}Service;

/**
* ${beanDesc}Service类
* @author ${copyRight.author}
* @date ${copyRight.since?string("yyyy/MM/dd")}
*/
@Service
public class ${beanName?cap_first}ServiceImpl implements ${beanName?cap_first}Service {

    @Autowired(required = false)
    private ${beanName?cap_first}Mapper<${beanName?cap_first}> ${beanName}Mapper;

    @Override
    public BaseMapper<${beanName?cap_first}> getMapper(){
        return ${beanName}Mapper;
    }

    @Override
    public Integer remove(Integer id) {
        ${beanName?cap_first} ${beanName} = new ${beanName?cap_first}();
        ${beanName}.setId(id);
        // 更新逻辑删除字段状态
        ${beanName}.setIsDel(true);
        return ${beanName}Mapper.update(${beanName});
    }
}