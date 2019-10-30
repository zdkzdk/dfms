package cn.dc.usercenter.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletResponseWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * 切面类，用来在所有返回远程nginx thymeleaf视图的方法中切入appName和uiPath，用来给nginx上的静态html加载css、js的标签提供地址
 */
@Component
@Aspect
public class ThymeleafAspect {
    @Value("${spring.application.name}")
    private String appName;
    @Value("${ui.path}")
    private String uiPath;

    /**
     * 定义切入点
     */
    @Pointcut(value = "execution(* cn.dc.usercenter.controller.SignController.*(..))")
    public void signControllerPointCut() {}
    /**
     * 定义通知
     */
    @Around(value = "signControllerPointCut()")
    public String aroundSign(ProceedingJoinPoint pjp) throws Throwable {

        Object[] objs = pjp.getArgs();
       for (Object obj : objs){
           if (obj instanceof Model){
               Model model = (Model) obj;
               model.addAttribute("appName", appName);
               model.addAttribute("uiPath", uiPath);
           }
       }


        String jsonDataFromDB = (String) pjp.proceed();
        return jsonDataFromDB;
    }


}
