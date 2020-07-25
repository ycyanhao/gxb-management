package com.youedata.base.tips;

import java.io.Serializable;

/**
 * restful 接口返回信息的实体类
 * @author hao.yan
 * @param <T>
 */
public class DCResponse<T> implements Serializable {
    /**
     * 请求接口正常返回
     */
    private static final int CODE_OK = 0;

    /**
     * 参数错误
     */
    private T result;
    private int code;
    private String msg;

    private DCResponse() {
    }

    private DCResponse(ResponseBuilder<T> builder) {
        this.result = builder.result;
        this.code = builder.code;
        this.msg = builder.msg;
    }

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
    public static <T> DCResponse<T> ok(T result){
        return new ResponseBuilder(result)
                .code(CODE_OK)
                .build();
    }

    public static DCResponse error(int code, String message) {
        return new ResponseBuilder("")
                .code(code)
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

        public ResponseBuilder code(int code) {
            this.code = code;
            if(CODE_OK == code){
                this.msg = "操作成功";
            }else{
                this.msg = "操作失败";
            }
            return this;
        }

        public ResponseBuilder result(T result) {
            this.result = result;
            return this;
        }

        public ResponseBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public DCResponse<T> build() {
            return new DCResponse(this);
        }
    }
}
