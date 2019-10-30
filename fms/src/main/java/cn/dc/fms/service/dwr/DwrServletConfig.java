package cn.dc.fms.service.dwr;

import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DwrServletConfig {
    @Bean("dwr-invoker")
    public ServletRegistrationBean servletRegistrationBean() {
        DwrSpringServlet servlet = new DwrSpringServlet();

        ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet, "/dwr/*");
        registrationBean.addInitParameter("debug", "true");
        registrationBean.addInitParameter("activeReverseAjaxEnabled","true");
        registrationBean.addInitParameter("initApplicationScopeCreatorsAtStartup","true");
        registrationBean.addInitParameter("crossDomainSessionSecurity","false");
        registrationBean.addInitParameter("maxWaitAfterWrite","500");
        registrationBean.setLoadOnStartup(1);

        return registrationBean;
    }
}
