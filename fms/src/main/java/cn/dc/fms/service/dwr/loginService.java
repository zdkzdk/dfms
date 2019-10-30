package cn.dc.fms.service.dwr;

import cn.dc.fms.dao.AdminDao;
import cn.dc.fms.dao.DB;
import cn.dc.fms.dao.ZhiGongDao;
import cn.dc.fms.orm.TAdmin;
import cn.dc.fms.orm.TZhigong;
import cn.dc.fms.utils.ScopeUtils;
import org.apache.shiro.web.session.HttpServletSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

/**
 * 登录服务
 *      使用DWR技术
 */
@RemoteProxy
@Service
public class loginService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private ZhiGongDao zhiGongDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @RemoteMethod
    public String login(String userName, String userPw, int userType) {
        String result = "no";
        //系统管理员登陆
        if (userType == 0) {
            TAdmin tAdmin = new TAdmin();
            tAdmin.setUserName(userName);
            tAdmin.setUserPw(userPw);
            List<TAdmin> admins = adminDao.findAll(new Specification<TAdmin>() {
                @Override
                public Predicate toPredicate(Root<TAdmin> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("userName"), userName), criteriaBuilder.equal(root.get("userPw"), userPw));
                }
            });

            if(admins == null || admins.size() == 0){
                return "no";
            }else {
                result = "yes";

           /*     WebContext ctx = WebContextFactory.get();
                HttpSession session = ScopeUtils.getSession();
                //HttpSession session = ctx.getSession();
                System.out.println(session);
                session.setAttribute("userType", 0);
                session.setAttribute("admin", tAdmin);*/
            }
        }else {
            TZhigong tZhigong = new TZhigong();
            tZhigong.setBianhao(userName);
            tZhigong.setLoginpw(userPw);
            List<TZhigong> zhigongs = zhiGongDao.findAll(new Specification<TZhigong>() {
                @Override
                public Predicate toPredicate(Root<TZhigong> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("bianhao"), userName), criteriaBuilder.equal(root.get("loginpw"), userPw));
                }
            });

            if(zhigongs == null || zhigongs.size() == 0){
                return "no";
            }else {
                result = "yes";

                WebContext ctx = WebContextFactory.get();
                HttpSession session = ctx.getSession();
                System.out.println(session);
                session.setAttribute("userType", 1);
                session.setAttribute("user", zhigongs.get(0));
            }
        }
        return result;
    }

    /**
     * 注销
     *  将redis中的信息和session中的信息清空
     * @return
     */
    @RemoteMethod
    public String logout(){
        HttpSession session = ScopeUtils.getSession();
        Enumeration<String> enumeration = session.getAttributeNames();
        //???遍历枚举

        while (enumeration.hasMoreElements()){
            session.removeAttribute(enumeration.nextElement());
        };
        session.removeAttribute("admin");
        session.removeAttribute("userType");
        redisTemplate.delete("admin");
        redisTemplate.delete("userType");
        return "success";
    }



    @RemoteMethod
    public String adminPwEdit(String userPwNew) {
        WebContext ctx = WebContextFactory.get();
        HttpSession session = ctx.getSession();
        TAdmin admin = (TAdmin) session.getAttribute("admin");

        String sql = "update t_admin set userPw=? where userId=?";
        Object[] params = {userPwNew, admin.getUserId()};
        DB mydb = new DB();
        mydb.doPstm(sql, params);

        return "yes";
    }
}