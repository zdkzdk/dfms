package cn.dc.fms.controller;

import cn.dc.fms.dao.DB;
import cn.dc.fms.dao.GongZiDao;
import cn.dc.fms.orm.TGongzi;
import cn.dc.fms.orm.TZhigong;
import cn.dc.fms.utils.GBKToUTF8Utils;
import cn.dc.fms.utils.ScopeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class GongZiController extends BaseController {
    @Autowired
    private GongZiDao gongZiDao;

    @Override
    @RequestMapping("gongzi")
    public String dispatcher(String type) {
        return super.dispatcher(type);
    }


    @RequestMapping("gongziAdd")
    public String gongziAdd(TGongzi gongzi, Model model) {

        gongZiDao.save(gongzi);
        model.addAttribute("message", GBKToUTF8Utils.gbkToUTF8("操作成功"));
        model.addAttribute("path", "gongzi?type=gongziMana");

        return "forward:/common/success.jsp";
    }
    @RequestMapping("gongziUpd")
    public String gongziUpd(TGongzi gongzi, Model model) {
        gongZiDao.save(gongzi);

        model.addAttribute("message", GBKToUTF8Utils.gbkToUTF8("操作成功"));
        model.addAttribute("path", "gongzi?type=gongziMana");

        return "forward:/common/success.jsp";
    }
    @RequestMapping("gongzi_me")
    public String gongzi_me(Model model) throws ServletException, IOException {
        TZhigong zhigong = ScopeUtils.getZhigong();
        model.addAttribute("gongzi",gongZiDao.findById(zhigong.getId()).get());
        return "gongzi/gongzi_me";
    }
    @RequestMapping("gongziMana")
    public String gongziMana(TGongzi gongzi, Model model){
        List<TGongzi> list = gongZiDao.findAll();
        model.addAttribute("gongziList",list );
        return "gongzi/gongziMana";
    }
}
