package ${codeConfig.apiPack};

import ${codeConfig.servicePack}.${beanName?cap_first}Service;
import com.base.BaseApi;
import com.base.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;


/**
* ${beanDesc}Controller
* @author ${copyRight.author}
* @since ${copyRight.since?string("yyyy/MM/dd")}
* @version ${copyRight.version}
*/
@Controller
@RequestMapping("/admin/${beanName}")
public class ${beanName?cap_first}Controller extends BaseApi {

    @Autowired
    ${beanName?cap_first}Service ${beanName}Service;

}
