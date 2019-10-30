package cn.dc.fms.controller;

import cn.dc.fms.dao.JingYingDao;
import cn.dc.fms.orm.TJingying;
import cn.dc.fms.utils.GBKToUTF8Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JingYingController extends BaseController {
    @Autowired
    private JingYingDao jingYingDao;
    @Override
    @RequestMapping("jingying")
    public String dispatcher(String type) {
        return super.dispatcher(type);
    }

    @RequestMapping("jingyingAdd")
    public String jingyingAdd(TJingying jingying, Model model) {
        jingYingDao.save(jingying);
        model.addAttribute("message", GBKToUTF8Utils.gbkToUTF8("操作成功"));
        model.addAttribute("path", "jingying?type=jingyingMana");
        return "forward:/common/success.jsp";
    }

    @RequestMapping("jingyingMana")
    public String jingyingMana(Model model){
        model.addAttribute("jingyingList", jingYingDao.findAll());
        return "jingying/jingyingMana";
    }
    @RequestMapping("jingyingList")
    public String jingyingList(Model model){
        model.addAttribute("jingyingList", jingYingDao.findAll());
        return "jingying/jingyingList";
    }
}
