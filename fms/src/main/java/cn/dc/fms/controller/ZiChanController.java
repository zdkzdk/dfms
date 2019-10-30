package cn.dc.fms.controller;

import cn.dc.fms.dao.CatelogDao;
import cn.dc.fms.dao.DB;
import cn.dc.fms.dao.ZhiGongDao;
import cn.dc.fms.dao.ZiChanDao;
import cn.dc.fms.orm.TZhigong;
import cn.dc.fms.orm.TZichan;
import cn.dc.fms.utils.GBKToUTF8Utils;
import cn.dc.fms.utils.ScopeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ZiChanController extends BaseController{
    @Autowired
    private ZiChanDao ziChanDao;
    @Autowired
    private CatelogDao catelogDao;
    @Override
    @RequestMapping("zichan")
    public String dispatcher(String type) {
        return super.dispatcher(type);
    }


    /**
     用来返回类别列表，在资产添加和编辑页面用来提供类别下拉框数据
     */
    @RequestMapping("zichanToAdd")
    private String zichanToAdd(Model model) {
        model.addAttribute("catelogList", catelogDao.findAll());
        return "zichan/zichanAdd";
    }
    /*
    zichan对象中也有个属性叫type，跟分发方法的type变量重名，导致将type代表的方法名当作zichan对象的type保存导致错误。
    前端用类型这个zichan对象没有的变量来传，要在后台手动把leixing这个变量set到type上。
     */
    @RequestMapping("zichanAdd")
    public String zichanAdd(TZichan zichan,Model model,String leixing) throws UnsupportedEncodingException {
        zichan.setType(leixing);
        String strType = "0".equals(leixing) ? "增加" : "减少";
        zichan.setStrType(GBKToUTF8Utils.gbkToUTF8(strType));
        zichan.setLbmc(new String(strType.getBytes("gbk"),"utf-8"));
        String strFangshi = "";
        switch (zichan.getFangshi()){
            case "0" :
                strFangshi = "自建";
                break;
            case "1" :
                strFangshi = "投资";
                break;
            case "2" :
                strFangshi = "出租";
                break;
        }
        zichan.setStrFangshi(GBKToUTF8Utils.gbkToUTF8(strFangshi));
        ziChanDao.save(zichan);
        model.addAttribute("message", GBKToUTF8Utils.gbkToUTF8("操作成功"));
        model.addAttribute("path", "zichan?type=zichanMana");
        return "forward:/common/success.jsp";
    }
    @RequestMapping("zichanMana")
    public String zichanMana(Model model){
        List<TZichan> list = ziChanDao.findAll();
        model.addAttribute("zichanList", list);
        return "zichan/zichanMana";
    }
    @RequestMapping("zichanList")
    public String zichanList(Model model){
        List<TZichan> list = ziChanDao.findAll();
        model.addAttribute("zichanList", list);
        return "zichan/zichanMana";
    }
}
