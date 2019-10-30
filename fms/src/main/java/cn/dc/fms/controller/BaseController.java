package cn.dc.fms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public abstract class BaseController {

    public String dispatcher(String type){
        if (!StringUtils.isEmpty(type)) return "forward:/" + type;
        return null;
    }
}
