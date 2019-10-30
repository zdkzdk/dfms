package cn.dc.fms.controller;

import cn.dc.fms.dao.FeiYongDao;
import cn.dc.fms.orm.TFeiyong;
import cn.dc.fms.utils.GBKToUTF8Utils;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class FeiYongController extends BaseController {
    @Autowired
    private FeiYongDao feiYongDao;

    @RequestMapping("feiyong")
    @Override
    public String dispatcher(String type) {
        return super.dispatcher(type);
    }

    @RequestMapping("feiyongAdd")
    public String feiyongAdd(TFeiyong feiyong, Model model) {
        feiYongDao.save(feiyong);
        model.addAttribute("message", GBKToUTF8Utils.gbkToUTF8("操作成功"));
        model.addAttribute("path", "feiyong?type=feiyongMana");

        return "forward:/common/success.jsp";

    }

    @RequestMapping("feiyongMana")
    public String feiyongMana(Model model) throws UnsupportedEncodingException {

        model.addAttribute("feiyongList", findALl());
        return "feiyong/feiyongMana";
    }

    @RequestMapping("feiyongList")
    public String feiyongList(Model model) throws UnsupportedEncodingException {
        model.addAttribute("feiyongList", findALl());
        return "feiyong/feiyongList";
    }

    private List<TFeiyong> findALl() throws UnsupportedEncodingException {
        List<TFeiyong> list = feiYongDao.findAll();
        for (TFeiyong feiyong : list) {
            String msg = getLxmc(Integer.parseInt(feiyong.getLeixing()));
            feiyong.setLeixing(new String(msg.getBytes("gbk"),"utf-8"));
        }
        return list;
    }
    private String getLxmc(int leixing) {
        String result = "";
        switch (leixing) {
            case 0:
                result = "收入";
                break;
            case 1:
                result = "支出";
                break;
            case 2:
                result = "报销";
                break;
        }
        return result;
    }
}
