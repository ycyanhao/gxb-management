package com.youedata.base.tips;

import java.util.Objects;

public class HealthCheckResponse {
    private Integer code;
    private Integer componentId;
    private String msg;

    public HealthCheckResponse() {
    }

    public HealthCheckResponse(Integer code, Integer componentId, String msg) {
        this.code = code;
        this.componentId = componentId;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "HealthCheckResponse{" +
                "code=" + code +
                ", componentId=" + componentId +
                ", msg='" + msg + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthCheckResponse that = (HealthCheckResponse) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(msg, that.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, componentId, msg);
    }
}
