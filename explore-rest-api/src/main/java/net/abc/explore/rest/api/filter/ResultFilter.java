package net.abc.explore.rest.api.filter;

import com.bobandata.web.common.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Auther: zhangwei
 * @Description: 初始化Result
 * @Date: Created in 下午1:01 2018/6/20
 */
@Component
public class ResultFilter implements Filter {

    private static Log log = LogFactory.getLog(ResultFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Result result = new Result();

        // 分页处理
        pageHandle(servletRequest, servletResponse, result);

        servletRequest.setAttribute("result", result);
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {

    }

    /**
     * 分页信息处理
     * @param servletRequest
     * @param servletResponse
     * @param result
     */
    private void pageHandle(ServletRequest servletRequest, ServletResponse servletResponse, Result result){
        String pageIndex = servletRequest.getParameter("pageIndex"),
                pageSize = servletRequest.getParameter("pageSize");
        // 空值判断
        if(pageIndex == null || pageSize == null)
           return;

        // debug模式下打印分页信息
        if(log.isDebugEnabled()){
            log.debug("PageIndex:"+pageIndex+" PageSize:"+pageSize);
        }

        // 过滤前后空格
        pageIndex = pageIndex.trim(); pageSize = pageSize.trim();

        // 数字判断
        if(!pageIndex.matches("^[0-9]*$") || !pageSize.matches("^[0-9]*$"))
            return;

        // 非0判断
        Integer pageIndexInt = Integer.valueOf(pageIndex), pageSizeInt = Integer.valueOf(pageSize);
        if(pageIndexInt == 0 || pageSizeInt == 0)
            return;

        // 所有判断通过后设置page信息
        result.setPageIndex(pageIndexInt);
        result.setPageSize(pageSizeInt);
    }
}
