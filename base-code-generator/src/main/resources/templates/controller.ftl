package ${codeConfig.controllerPack};

import ${codeConfig.servicePack}.${beanName?cap_first}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
* ${beanDesc}Controller
* @author ${copyRight.author}
* @since ${copyRight.since?string("yyyy/MM/dd")}
* @version ${copyRight.version}
*/
@Controller
@RequestMapping("/${beanName}")
public class ${beanName?cap_first}Controller {

    @Autowired
    ${beanName?cap_first}Service ${beanName}Service;

}
