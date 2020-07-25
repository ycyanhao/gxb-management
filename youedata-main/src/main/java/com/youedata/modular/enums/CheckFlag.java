package com.youedata.modular.enums;

/**
 * @author hao.yan
 */

public enum CheckFlag {

    /**
     * 任务核对状态
     */
    WAIT("0", "待核对"),
    PROCESS("1", "核对中"),
    SUCCESS("2", "核对成功");

    private String code;
    private String desc;

    CheckFlag(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static CheckFlag getByCode(String code) {
        CheckFlag[] values = values();
        for (CheckFlag sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        CheckFlag[] values = values();
        for (CheckFlag sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
