package cn.dc.fms.service.shiro.config;



import cn.dc.fms.service.shiro.matcher.CredentialsMatcher;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfiguration {

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/loginpage");
        bean.setSuccessUrl("/main");

        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/loginpage*", "anon"); //表示可以匿名访问
       // filterChainDefinitionMap.put("/admin/*", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/admin/*", "anon");
        filterChainDefinitionMap.put("/logout*","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/fomts/**","anon");
        filterChainDefinitionMap.put("/**", "anon");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }
    //配置核心安全事务管理器
    @Bean(name="securityManager")
    public SecurityManager securityManager(
                                           @Qualifier("sessionManager") SessionManager sessionManager,
                                           @Qualifier("cookieRememberMeManager")RememberMeManager cookieRememberMeManager,
                                           @Qualifier("redisCacheManager")RedisCacheManager redisCacheManager
    ) {
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        //@Qualifier("authRealm") AuthRealm authRealm,
        //manager.setRealm(authRealm);
        manager.setSessionManager(sessionManager);
        manager.setRememberMeManager(cookieRememberMeManager);
        // 自定义缓存实现 使用redis
        manager.setCacheManager(redisCacheManager);

        return manager;
    }

//copy===========================================
    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean("redisCacheManager")
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.100.130");
        redisManager.setPort(6379);
        redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(0);
        // redisManager.setPassword(password);

        return redisManager;
    }
  //@Bean("sessionManager")
  //  public DefaultWebSessionManager sessionManager(@Qualifier("redisSessionDAO")RedisSessionDAO redisSessionDAO) {
  //      CustomSessionManager sessionManager = new CustomSessionManager();
  //
  //      sessionManager.setSessionIdCookieEnabled(true);
  //      //设置sessionDao
  //      //sessionManager.setSessionDAO(redisSessionDAO);
  //      return sessionManager;
  //
  //  }
    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }


    //配置自定义的权限登录器
    //@Bean(name="authRealm")
    //public AuthRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
    //    AuthRealm authRealm=new AuthRealm();
    //    authRealm.setCredentialsMatcher(matcher);
    //    return authRealm;
    //}
    //配置自定义的密码比较器
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }
    //用于thymeleaf模板使用shiro标签
    //@Bean
    //public ShiroDialect shiroDialect() {
    //    return new ShiroDialect();
    //}
    //Shiro生命周期处理器
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    //授权所用配置
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
}
