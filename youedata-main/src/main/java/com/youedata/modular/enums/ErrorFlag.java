package com.youedata.modular.enums;

public enum ErrorFlag {
    /**
     * 人为确认异常状态
     */
    DEFAULT("0", "默认-灰色"),
    Success("1", "确认无异常"),
    Error("2", "确认异常");

    private String code;
    private String desc;

    ErrorFlag(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ErrorFlag getByCode(String code) {
        ErrorFlag[] values = values();
        for (ErrorFlag sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        ErrorFlag[] values = values();
        for (ErrorFlag sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
