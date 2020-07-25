package com.youedata.modular.enums;

/**
 * @author TC
 * @version 1.0<br>
 * @title: AiStatusFlag
 * @projectName com.youedata.modular.enums
 * @date 2020/01/10 13:02
 * @description: 仅供tmp_ai_flag临时状态使用
 */
public enum AiStatusFlag {

    /**
     * 0,0：初始值 ner,pdf
     */
    INIT("0,0","初始值"),
    /**
     * 0:默认
     */
    DEFAULT("0", "默认"),
    /**
     * 1:识别中
     */
    PROCESS("1", "识别中"),
    /**
     * 2:识别失败
     */
    FAIL("2", "识别失败"),
    /**
     * 3:识别成功
     */
    SUCCESS("3", "识别成功");

    private String code;
    private String desc;

    AiStatusFlag(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static AiStatusFlag getByCode(String code) {
        AiStatusFlag[] values = values();
        for (AiStatusFlag sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        AiStatusFlag[] values = values();
        for (AiStatusFlag sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
