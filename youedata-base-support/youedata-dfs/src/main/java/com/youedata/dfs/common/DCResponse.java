package com.youedata.dfs.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * restful 接口返回信息的实体类
 * @param <T>
 */
@ApiModel(description = "服务器端返回结果")
public class DCResponse<T> {
    /**
     * 请求接口正常返回
     */
    public static final int CODE_OK = 0;

    /**
     * 参数格式错误
     */
    public static final int CODE_PARAM_VALID_ERROR = 100;

    private T result;
    @ApiModelProperty(value = "代码",name = "code",example = "0")
    private int code;

    @ApiModelProperty(value = "描述",name = "msg",example = "成功")
    private String msg;

    private DCResponse() {
    }

    private DCResponse(ResponseBuilder<T> builder) {
        this.result = builder.result;
        this.code = builder.code;
        this.msg = builder.msg;
    }

    @SuppressWarnings("unchecked")
	public T getResult() {
        if(result == null){
            return (T) "";
        }
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param result
     * @param <T>
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> DCResponse<T> ok(T result){
        return new ResponseBuilder(result)
                .code(CODE_OK)
                .build();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static DCResponse error(int code, String message) {
        return new ResponseBuilder("")
                .code(code)
                .msg(message)
                .build();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static DCResponse error(String message) {
    	return new ResponseBuilder("")
    			.code(CODE_PARAM_VALID_ERROR)
    			.msg(message)
    			.build();
    }

    public static class ResponseBuilder<T> {
        private T result;
        private int  code;

        private String msg;

        public ResponseBuilder() {
            this.result = null;
        }

        public ResponseBuilder(T result) {
            this.result = result;
        }

        @SuppressWarnings("rawtypes")
		public ResponseBuilder code(int code) {
            this.code = code;
            if(CODE_OK == code){
                this.msg = "操作成功";
            }else{
                this.msg = "操作失败";
            }
            return this;
        }

        @SuppressWarnings("rawtypes")
		public ResponseBuilder result(T result) {
            this.result = result;
            return this;
        }

        @SuppressWarnings("rawtypes")
		public ResponseBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
		public DCResponse<T> build() {
            return new DCResponse(this);
        }
    }
}
