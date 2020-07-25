package com.youedata.modular.enums;

/**
 * @author Administrator
 */

public enum CompanyDetailType {
    /**
     * 类型 （00-借方发生额  , 01-贷方发生额）
     */
    DEBTOR("00", "借方发生额"),
    CREDIT("01", "贷方发生额");

    private String code;
    private String desc;

    CompanyDetailType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static CompanyDetailType getByCode(String code) {
        CompanyDetailType[] values = values();
        for (CompanyDetailType sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        CompanyDetailType[] values = values();
        for (CompanyDetailType sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
