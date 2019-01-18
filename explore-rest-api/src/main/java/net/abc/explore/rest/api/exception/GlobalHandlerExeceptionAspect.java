package net.abc.explore.rest.api.exception;

import net.abc.explore.rest.api.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhangwei
 * @date: 23:04/2019-01-15
 */
@Aspect
@Component
public class GlobalHandlerExeceptionAspect {

    private static final Logger log = LoggerFactory.getLogger(GlobalHandlerExeceptionAspect.class);

    private static final String X_Real_IP = "X-Real-IP";

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controller(){}


    @Before("controller()")
    public void before(JoinPoint joinPoint){
        if(log.isInfoEnabled()){
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            log.info("url={},method={},remote-ip={},handler={}.{},args={}",
                    request.getRequestURI(), request.getMethod(), request.getHeader(X_Real_IP), joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), joinPoint.getArgs());
        }
    }

    @AfterReturning(pointcut = "controller()", returning = "result")
    public void after(JoinPoint joinPoint, Object result){
        if(log.isDebugEnabled()){
           log.debug("handler={}.{},args={},result={}",
                   joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName(), result.toString());
        }
    }

    @AfterThrowing(pointcut = "controller()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex){
        log.error(String.format("handler=%s.%s,args=%s,ex=%s",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName(), ex.getMessage()),
                ex);
        Result result = resultByArgs(joinPoint.getArgs());
        if(result != null){
            result.error(ex.getMessage());
        }
    }

    private static Result resultByArgs(Object[] args){
        for(Object arg : args){
            if(arg instanceof Result){
                return (Result) arg;
            }
        }
        return null;
    }
}
