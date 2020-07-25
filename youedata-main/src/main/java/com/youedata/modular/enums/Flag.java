package com.youedata.modular.enums;

public enum Flag {
    /**
     *
     */
    YES("Y", "是"),
    NO("N", "否");

    private String code;
    private String desc;

    Flag(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Flag getByCode(String code) {
        Flag[] values = values();
        for (Flag sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        Flag[] values = values();
        for (Flag sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
