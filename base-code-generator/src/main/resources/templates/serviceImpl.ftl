package ${codeConfig.servicePack}.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${codeConfig.beanPack}.${beanName?cap_first};
import ${codeConfig.daoPack}.${beanName?cap_first}Mapper;

/**
* ${beanDesc}Service类
* @author ${copyRight.author}
* @since ${copyRight.since?string("yyyy/MM/dd")}
*/
@Service
public class ${beanName?cap_first}ServiceImpl implements ${beanName?cap_first}Service {

    @Autowired
    private ${beanName?cap_first}Mapper<${beanName?cap_first}> ${beanName}Mapper;

    @Override
    public BaseMapper<${beanName?cap_first}> getMapper(){
        return ${beanName}Mapper;
    }
}