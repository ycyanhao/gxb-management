package com.youedata.dfs.aop;

import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.exception.GunsException;
import com.youedata.dfs.common.exception.BizExceptionEnum;
import com.youedata.dfs.common.DCResponse;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

import static com.stylefeng.guns.core.support.HttpKit.getRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截jwt相关异常
     */
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorTip jwtException(JwtException e) {
        return new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage());
    }
    
    /**
     * 拦截业务异常
     */
    @SuppressWarnings("rawtypes")
	@ExceptionHandler(GunsException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DCResponse notFount(GunsException e) {
    	return DCResponse.error(e.getCode(), e.getMessage());
    }
    
    /**
     * 拦截未知的运行时异常
     */
    @SuppressWarnings("rawtypes")
	@ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DCResponse notFount(RuntimeException e) {
        getRequest().setAttribute("tip", "服务器未知运行时异常");
        log.error("运行时异常:", e);
        if( e instanceof MaxUploadSizeExceededException ){//
        	return DCResponse.error(BizExceptionEnum.FILE_MAX_LIMIT.getCode(), BizExceptionEnum.FILE_MAX_LIMIT.getMessage());
        }
        return DCResponse.error(BizExceptionEnum.SERVER_ERROR.getCode(), BizExceptionEnum.SERVER_ERROR.getMessage());
    }
}
