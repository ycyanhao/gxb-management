package com.youedata.modular.filter;

import cn.stylefeng.roses.core.util.RenderUtil;
import com.youedata.base.auth.jwt.JwtTokenUtil;
import com.youedata.base.tips.ErrorTip;
import com.youedata.config.properties.SwaggerProperties;
import com.youedata.modular.service.IUserService;
import com.youedata.sys.core.exception.enums.BizExceptionEnum;
import com.youedata.sys.core.util.RedisUtil;
import com.youedata.sys.core.util.UserKit;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:04
 */
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // monitor
        if (request.getServletPath().startsWith("/monitor")) {
            chain.doFilter(request, response);
            return;
        }
        // 探针放行
        if (request.getServletPath().startsWith("/healthCheck")) {
            chain.doFilter(request, response);
            return;
        }
        // 算法放行
        if (request.getServletPath().startsWith("/file/upload") ||
                request.getServletPath().startsWith("/dataImport/bank/lead")) {
            log.info("算法放行,path:{}", request.getServletPath());
            chain.doFilter(request, response);
            return;
        }
        //swagger相关
        if (swaggerProperties.getApiOpen()) {
            if (request.getServletPath().startsWith("/" + "swagger") ||
                    request.getServletPath().equals("/" + "v2/api-docs") ||
                    request.getServletPath().contains("springfox-swagger-ui")) {
                chain.doFilter(request, response);
                return;
            }
        }
        // auth
        if (request.getServletPath().startsWith("/auth")) {
            chain.doFilter(request, response);
            return;
        }

        //检查jwt的token
        final String requestHeader = request.getHeader(UserKit.USERTOKEN);
        logger.info("请求头信息-{}", requestHeader);
        String authToken = null;
        if (requestHeader != null) {
            authToken = requestHeader;
            //验证token是否过期,包含了验证jwt是否正确
            try {
                logger.info("请求头token信息-{}", authToken);
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {//验证token是否失效
                    RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_EXPIRED.getCode(), BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
                    return;
                }
                if (!redisUtil.hasKey(authToken)) {//根据当前的token比对下Redis存储信息
                    RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_EXPIRED_NOT_EXIST.getCode(), BizExceptionEnum.TOKEN_EXPIRED_NOT_EXIST.getMessage()));
                    return;
                }
            } catch (JwtException e) {
                e.printStackTrace();
                //有异常就是token解析失败
                RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
                return;
            }
        } else {
            //header没有带userToken字段
            RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
            return;
        }
        String userStr = redisUtil.get(authToken);
        logger.info("请求头token信息-{},用户信息-{}",authToken, userStr);
        if (StringUtils.isBlank(userStr)) {
            RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_EXPIRED_NOT_EXIST.getCode(), BizExceptionEnum.TOKEN_EXPIRED_NOT_EXIST.getMessage()));
            return;
        }
        chain.doFilter(request, response);
    }
}