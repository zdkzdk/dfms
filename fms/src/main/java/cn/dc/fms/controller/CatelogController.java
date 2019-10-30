package cn.dc.fms.controller;

import cn.dc.fms.dao.CatelogDao;
import cn.dc.fms.orm.TCatelog;
import cn.dc.fms.utils.GBKToUTF8Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
public class CatelogController extends BaseController {
    @Autowired
    private CatelogDao catelogDao;
    @Override
    @RequestMapping("catelog")
    public String dispatcher(String type) {
        return super.dispatcher(type);
    }
    @RequestMapping("catelogAdd")
    public String catelogAdd(TCatelog catelog, Model model) {
        catelog.setDel("no");
        catelogDao.save(catelog);
        model.addAttribute("message", GBKToUTF8Utils.gbkToUTF8("操作成功"));
        model.addAttribute("path", "catelog?type=catelogMana");
        return "forward:/common/success.jsp";
    }
    @RequestMapping("catelogUpd")
    public String catelogUpd(TCatelog catelog, Model model) {
        catelogDao.save(catelog);
        model.addAttribute("message", GBKToUTF8Utils.gbkToUTF8("操作成功"));
        model.addAttribute("path", "catelog?type=catelogMana");
        return "forward:/common/success.jsp";
    }
    @RequestMapping("catelogDel")
    public String catelogDel(TCatelog catelog, Model model) {
        catelogDao.delete(catelog);
        model.addAttribute("message", GBKToUTF8Utils.gbkToUTF8("操作成功"));
        model.addAttribute("path", "catelog?type=catelogMana");
        return "forward:/common/success.jsp";
    }
    @RequestMapping("catelogMana")
    public String catelogMana(Model model) {

        List<TCatelog> list = catelogDao.findAll();
        model.addAttribute("catelogList", list);

        return "catelog/catelogMana";
    }
}
