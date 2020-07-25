package com.youedata.base.exception;

import org.springframework.validation.BindingResult;

/**
 * 封装业务异常
 *
 * @author hao.yan
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    private String message;

    public BusinessException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(BindingResult result) {
        this.code = 99999;
        this.message = result.getFieldError().getDefaultMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
