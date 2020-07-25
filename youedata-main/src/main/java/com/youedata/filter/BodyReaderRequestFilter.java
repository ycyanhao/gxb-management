package com.youedata.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 实现输入流就可以读取任意次
 * @author liujijun
 *
 */

public class BodyReaderRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Do nothing
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        //swagger相关
/*        if (request.getServletPath().startsWith("/" + "swagger") ||
                request.getServletPath().equals("/" + "v2/api-docs")||
                request.getServletPath().contains("springfox-swagger-ui")) {
        	filterChain.doFilter(request, response);
            return;
        }*/
        BodyReaderRequestWrapper requestWrapper  = new BodyReaderRequestWrapper(request);
        filterChain.doFilter(requestWrapper,response);
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
