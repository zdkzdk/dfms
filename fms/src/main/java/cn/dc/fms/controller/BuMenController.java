package cn.dc.fms.controller;

import cn.dc.fms.dao.BuMenDao;
import cn.dc.fms.orm.TBumen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BuMenController extends BaseController{
    @Autowired
    private BuMenDao buMenDao;

    @Override
    @RequestMapping("bumen")
    public String dispatcher(String type) {
        return super.dispatcher(type);
    }

    @RequestMapping("bumenMana")
    private String findAllBuMen(Model model){
        model.addAttribute("bumenList", buMenDao.findAll());
       return "bumen/bumenMana";
    }
    @RequestMapping("bumenSele")
    private String bumenSele(Model model){
        model.addAttribute("bumenList", buMenDao.findAll());
        return "bumen/bumenSele";
    }
    /*
    jpa中添加和更新用同一个方法
     */
    @RequestMapping({"bumenAdd","bumenUpd"})
    private String bumenAdd(TBumen bumen){
        buMenDao.save(bumen);
        return "forward:/bumenMana";
    }

    @RequestMapping("bumenDel")
    private String bumenDel(TBumen bumen){
        buMenDao.delete(bumen);
        return "forward:/bumenMana";
    }

}
