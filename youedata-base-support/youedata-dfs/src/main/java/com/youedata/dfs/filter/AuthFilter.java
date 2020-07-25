package com.youedata.dfs.filter;

import io.jsonwebtoken.JwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.stylefeng.guns.core.util.RenderUtil;
import com.youedata.dfs.common.ApiResult;
import com.youedata.dfs.common.BizExceptionEnum;
import com.youedata.dfs.common.JwtTokenUtil;
import com.youedata.dfs.config.JwtProperties;
import com.youedata.dfs.config.SwaggerProperties;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author liujijun
 */
public class AuthFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;
    
    @Autowired
    private SwaggerProperties swaggerProperties;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    	
        //swagger相关
        if( swaggerProperties.getApiOpen() ) {
        	if (request.getServletPath().startsWith("/" + "swagger") ||
        			request.getServletPath().equals("/" + "v2/api-docs")||
        			request.getServletPath().contains("springfox-swagger-ui")) {
        		chain.doFilter(request, response);
        		return;
        	}
        }
    	
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        logger.info("requestHeader-"+requestHeader);
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                	
                    RenderUtil.renderJson(response, ApiResult.fail(BizExceptionEnum.TOKEN_EXPIRED.getMessage()) );
                    return;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                RenderUtil.renderJson(response, ApiResult.fail(BizExceptionEnum.TOKEN_ERROR.getMessage()) );
                return;
            }
        } else {
            //header没有带Bearer字段
            RenderUtil.renderJson(response, ApiResult.fail(BizExceptionEnum.TOKEN_ERROR.getMessage()) );
            return;
        }
        chain.doFilter(request, response);
    }
}