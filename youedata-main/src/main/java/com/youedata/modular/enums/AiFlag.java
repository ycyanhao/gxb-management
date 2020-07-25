package com.youedata.modular.enums;

public enum AiFlag {
    /**
     * 算法识别状态
     */
    WAIT("00", "待识别"),
    PROCESS("01", "识别中"),
    SUCCESS("02", "识别成功"),
    FAIL("03", "识别失败");

    private String code;
    private String desc;

    AiFlag(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static AiFlag getByCode(String code) {
        AiFlag[] values = values();
        for (AiFlag sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        AiFlag[] values = values();
        for (AiFlag sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
