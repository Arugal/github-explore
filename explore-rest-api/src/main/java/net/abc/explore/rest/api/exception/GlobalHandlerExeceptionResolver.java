package net.abc.explore.rest.api.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: zhangwei
 * @date: 19:50/2019-01-05
 */
public class GlobalHandlerExeceptionResolver implements HandlerExceptionResolver {

    private static final Log logger = LogFactory.getLog(GlobalHandlerExeceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        HandlerMethod method = (HandlerMethod) handler;
        String msg = String.format("HandlerClass:%s , HandlerMethod:%s , exMeg:%s", method.getBeanType().getName(), method.getMethod().getName(), ex.getMessage());
        logger.error(msg, ex);
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> modelmap = modelAndView.getModelMap();
        modelmap.put("msg", msg);
        modelmap.put("ex", ex);
        return modelAndView;
    }
}
