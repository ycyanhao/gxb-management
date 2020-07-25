package com.youedata.modular.enums;

public enum AiIdentityFlag {
    Normal("0", "正常"),
    Error("1", "异常");

    private String code;
    private String desc;

    AiIdentityFlag(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static AiIdentityFlag getByCode(String code) {
        AiIdentityFlag[] values = values();
        for (AiIdentityFlag sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        AiIdentityFlag[] values = values();
        for (AiIdentityFlag sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
