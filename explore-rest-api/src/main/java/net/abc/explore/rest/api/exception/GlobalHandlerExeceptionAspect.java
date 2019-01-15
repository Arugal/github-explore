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


    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controller(){}


    @Before("controller()")
    public void before(JoinPoint joinPoint){
        if(log.isInfoEnabled()){
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            log.info("url={},method={},ip={},handler={}.{},args={}",
                    request.getRequestURI(), request.getMethod(), request.getRemoteAddr(), joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), joinPoint.getArgs());
        }
    }

    @AfterReturning("controller()")
    public void after(JoinPoint joinPoint){
        if(log.isDebugEnabled()){
           Result result = resultByArgs(joinPoint.getArgs());
           log.debug("handler={}.{},args={},resul={}",
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
