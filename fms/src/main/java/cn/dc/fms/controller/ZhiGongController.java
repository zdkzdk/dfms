package cn.dc.fms.controller;

import cn.dc.fms.dao.DB;
import cn.dc.fms.dao.GongZiDao;
import cn.dc.fms.dao.ZhiGongDao;
import cn.dc.fms.orm.TZhigong;
import cn.dc.fms.utils.GBKToUTF8Utils;
import cn.dc.fms.utils.ScopeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ZhiGongController extends BaseController{
    @Autowired
    private ZhiGongDao zhiGongDao;
    @Override
    @RequestMapping("zhigong")
    public String dispatcher(String type) {
        return super.dispatcher(type);
    }

    @RequestMapping("zhigong_me")
    private String zhigong_me(Model model){

        TZhigong zhigong = ScopeUtils.getZhigong();
        TZhigong zhigongAll = zhiGongDao.findById(zhigong.getId()).get();
        model.addAttribute("zhigong", zhigongAll);
       return "zhigong/zhigong_me";
    }
    @RequestMapping("zhigongUpd_me")
    private String zhigongUpd_me(TZhigong zhigong){
        zhiGongDao.save(zhigong);
        return "zhigong/zhigong_me";
    }

    @RequestMapping("zhigongMana")
    private String zhigongMana(Model model,String bianhao){
        List<TZhigong> zhigongs = new ArrayList<>();
        if (StringUtils.isEmpty(bianhao)){
            zhigongs = zhiGongDao.findAll();
        }else {
            zhigongs = zhiGongDao.findAll(new Specification<TZhigong>() {
                @Override
                public Predicate toPredicate(Root<TZhigong> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.like(root.get("bianhao").as(String.class), "%" + bianhao + "%");
                }
            });
        }


        model.addAttribute("bianhao", bianhao);
        model.addAttribute("zhigongList", zhigongs);

        return "zhigong/zhigongMana";
    }

    /*
        查的是没有工资的员工
     */
    @RequestMapping("zhigongList")
    private void zhigongList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setAttribute("zhigongList", zhiGongDao.zhigongList());
        req.getRequestDispatcher("admin/zhigong/zhigongList.jsp").forward(req, res);
    }

    @RequestMapping("zhigongAdd")

    public String zhigongAdd(TZhigong zhigong,Model model) {
        zhigong.setDel("no");
        zhiGongDao.save(zhigong);
        model.addAttribute("message", GBKToUTF8Utils.gbkToUTF8("操作成功"));
        model.addAttribute("path", "zhigong?type=zhigongMana");


        return  "forward:/common/success.jsp";
    }



    private List getZhigongList(String sql) {
        List zhigongList = new ArrayList();
        Object[] params = {};
        DB mydb = new DB();
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {
                TZhigong zhigong = new TZhigong();
                zhigong.setId(rs.getInt("id"));
                zhigong.setBumen_id(rs.getInt("bumen_id"));
                zhigong.setBianhao(rs.getString("bianhao"));
                zhigong.setLoginpw(rs.getString("loginpw"));
                zhigong.setXingming(rs.getString("xingming"));
                zhigong.setXingbie(rs.getString("xingbie"));
                zhigong.setRuzhi(rs.getString("ruzhi"));

                zhigong.setBmmc(rs.getString("bmmc"));
                zhigong.setXishu(rs.getDouble("xishu"));
                zhigongList.add(zhigong);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mydb.closed();
        return zhigongList;
    }
}
