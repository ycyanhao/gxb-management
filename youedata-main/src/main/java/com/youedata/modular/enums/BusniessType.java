package com.youedata.modular.enums;

public enum BusniessType {
    /**
     * 企业明细账类型 （00：银行借方；01：银行贷方）
     */
    BANK_LEND("00", "银行借方"),
    BANK_BORROW("01", "银行贷方");

    private String code;
    private String desc;

    BusniessType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static BusniessType getByCode(String code) {
        BusniessType[] values = values();
        for (BusniessType sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        BusniessType[] values = values();
        for (BusniessType sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
