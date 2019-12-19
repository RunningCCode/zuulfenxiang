package com.yunhua.czc.zuulfenxiang.gatewayconfig.aspect;

import com.yunhuakeji.component.aop.util.AopUtil;
import com.yunhuakeji.component.base.util.JsonUtil;
import com.yunhuakeji.component.logger.annotation.PrintLog;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author huangjinlong
 */
@Log4j2
@Aspect
@Component
public class LogAspect4This {
    /**
     * 定义切面
     */
    @Pointcut("execution(public * com.yunhua..*.client..*.*(..))")
    public void logger() {}

    /**
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("logger()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Method method = AopUtil.around2Method(proceedingJoinPoint);

        Object result = proceedingJoinPoint.proceed();

        boolean isPrintLog = false;

        PrintLog printLog = method.getAnnotation(PrintLog.class);
        if (printLog == null) {
            /**
             * 看这个类上面有没有注解
             */
            printLog = proceedingJoinPoint.getTarget().getClass().getAnnotation(PrintLog.class);
            if (printLog != null) {
                isPrintLog = printLog.value();
            } else {
                isPrintLog = true;
            }
        } else {
            isPrintLog = printLog.value();
        }
        if (isPrintLog) {
            log.info("出参:"+JsonUtil.toJsonStringQuietly(result));
        } else {
            log.info("已关闭打印出参");
        }
        return result;
    }
}