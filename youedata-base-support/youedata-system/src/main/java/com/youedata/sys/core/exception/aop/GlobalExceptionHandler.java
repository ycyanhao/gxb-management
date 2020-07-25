package com.youedata.sys.core.exception.aop;

import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.youedata.base.auth.context.LoginContextHolder;
import com.youedata.base.auth.exception.AuthException;
import com.youedata.base.auth.exception.PermissionException;
import com.youedata.base.auth.exception.enums.AuthExceptionEnum;
import com.youedata.base.exception.BusinessException;
import com.youedata.base.tips.DCResponse;
import com.youedata.base.tips.ErrorTip;
import com.youedata.sys.core.exception.InvalidKaptchaException;
import com.youedata.sys.core.exception.enums.BizExceptionEnum;
import com.youedata.sys.core.log.LogManager;
import com.youedata.sys.core.log.factory.LogTaskFactory;
import com.youedata.sys.core.util.UserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;
import static cn.stylefeng.roses.core.util.HttpContext.getRequest;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
@Order(-100)
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 认证异常--认证失败（账号密码错误，账号被冻结，token过期等）
     */
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponseData unAuth(AuthException e) {
        return new ErrorResponseData(e.getCode(), e.getMessage());
    }

    /**
     * 认证异常--没有访问权限
     */
    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponseData permissionExpection(PermissionException e) {
        return new ErrorResponseData(e.getCode(), e.getMessage());
    }

    /**
     * 认证异常--验证码错误异常
     */
    @ExceptionHandler(InvalidKaptchaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseData credentials(InvalidKaptchaException e) {
        String username = getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "验证码错误", getIp()));
        return new ErrorResponseData(AuthExceptionEnum.VALID_CODE_ERROR.getCode(), AuthExceptionEnum.VALID_CODE_ERROR.getMessage());
    }

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData business(ServiceException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(UserHolder.getUserId(), UserHolder.getUserName() , e));

        getRequest().setAttribute("tip", e.getMessage());
        log.error("业务异常:", e);
        return new ErrorResponseData(e.getCode(), e.getMessage());
    }

    /**
     * redis 连接失败
     */
    @ExceptionHandler(RedisConnectionFailureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData redisConnectionFailure(RuntimeException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(UserHolder.getUserId(), UserHolder.getUserName() , e));

        getRequest().setAttribute("tip", "Redis连接失败异常");
        log.error("运行时异常:", e);
        return new ErrorResponseData(BizExceptionEnum.REDIS_CONNECTION_FAILURE.getCode(), BizExceptionEnum.REDIS_CONNECTION_FAILURE.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData notFound(RuntimeException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(UserHolder.getUserId(), UserHolder.getUserName() , e));

        getRequest().setAttribute("tip", "服务器未知运行时异常");
        log.error("运行时异常:", e);
        return new ErrorResponseData(BizExceptionEnum.SERVER_ERROR.getCode(), BizExceptionEnum.SERVER_ERROR.getMessage());
    }

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ErrorTip business(BusinessException e) {
        log.error("业务异常:{}", e.getMessage());
        return new ErrorTip(e.getCode(), e.getMessage());
    }


    /**
     * 拦截校验异常
     */
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DCResponse validException(ConstraintViolationException e) {
        return DCResponse.error(10000, e.getMessage());
    }


}
