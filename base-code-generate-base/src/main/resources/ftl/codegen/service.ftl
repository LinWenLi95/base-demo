package ${codeConfig.servicePack};

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
public class ${beanName?cap_first}Service extends  BaseServiceSupport<${beanName?cap_first}>{

    @Autowired
    private ${beanName?cap_first}Mapper<${beanName?cap_first}> ${beanName}Mapper;

    @Override
    public IMapper<${beanName?cap_first}> getMapper(){
        return ${beanName}Mapper;
    }
}