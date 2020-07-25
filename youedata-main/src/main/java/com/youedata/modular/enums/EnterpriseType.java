package com.youedata.modular.enums;

/**
 * @author Administrator
 */

public enum EnterpriseType {
    /**
     * 类型（00-银行  , 01-企业）
     */
    BANK("00", "银行"),
    COMPANY("01", "企业");

    private String code;
    private String desc;

    EnterpriseType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static EnterpriseType getByCode(String code) {
        EnterpriseType[] values = values();
        for (EnterpriseType sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        EnterpriseType[] values = values();
        for (EnterpriseType sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
