package cn.dc.fms.utils;

import cn.dc.fms.orm.TAdmin;
import cn.dc.fms.orm.TZhigong;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ScopeUtils {
    public static TAdmin getAdmin() {
        return (TAdmin)getSession().getAttribute("admin");
    }
    public static TZhigong getZhigong() {
        return (TZhigong)getSession().getAttribute("user");
    }
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static HttpServletRequest getRequest()
    {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();

        return request;
    }
}
