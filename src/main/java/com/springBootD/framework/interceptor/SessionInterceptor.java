package com.springBootD.framework.interceptor;

import org.springframework.stereotype.Component;

import com.springBootD.framework.controller.BaseController;
import com.springBootD.framework.utils.HttpSessionHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 静态调用session的拦截器
 *
 */
@Aspect
@Component
public class SessionInterceptor extends BaseController {

    @Pointcut("execution(* com.springBootD.application.*..controller.*.*(..))")
    public void cutService() {
    }

    @Around("cutService()")
    public Object sessionKit(ProceedingJoinPoint point) throws Throwable {

        HttpSessionHolder.put(super.getHttpServletRequest().getSession());
        try {
            return point.proceed();
        } finally {
            HttpSessionHolder.remove();
        }
    }
}
