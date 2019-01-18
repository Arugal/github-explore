package net.abc.explore.rest.api.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: zhangwei
 * @date: 14:28/2019-01-18
 */
//@Component
public class HostFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(HostFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;


        chain.doFilter(servletRequest, servletResponse);
    }
}
