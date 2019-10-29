package com.diandian.aspect;

import com.diandian.exception.ParamException;
import com.diandian.model.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 切面
 */
@Aspect
public class SessionAspect {

    /**
     * 校验身份，查看session中是否存在用户信息
     */
//    @Before("execution(* com.diandian.controller.*.*(..))")
    public void identityCheck() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session=attr.getRequest().getSession();
        User user = (User)session.getAttribute("user");
        if (user == null) {
            throw new ParamException("身份异常，请重新登录!");
        }
    }
}
