package com.youedata.modular.enums;

public enum ExceptionFlag {
    /**
     * 异常标记（0-未标注；1-无异常；2-异常）
     */
    NONE("0", "未标注"),
    NO("1", "未标注"),
    YES("2", "标注有问题");

    private String code;
    private String desc;

    ExceptionFlag(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ExceptionFlag getByCode(String code) {
        ExceptionFlag[] values = values();
        for (ExceptionFlag sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        ExceptionFlag[] values = values();
        for (ExceptionFlag sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
