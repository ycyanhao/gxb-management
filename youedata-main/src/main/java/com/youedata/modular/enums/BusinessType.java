package com.youedata.modular.enums;

/**
 * @author hao.yan
 */

public enum BusinessType {
    /**
     * 类型 （00-借方 , 01-贷方）
     */
    DEBTOR("00", "借方"),
    CREDIT("01", "贷方");

    private String code;
    private String desc;

    BusinessType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static BusinessType getByCode(String code) {
        BusinessType[] values = values();
        for (BusinessType sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        BusinessType[] values = values();
        for (BusinessType sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
