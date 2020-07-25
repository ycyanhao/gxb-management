package com.youedata.modular.enums;

/**
 * @author TC
 * @version 1.0<br>
 * @title: AiType
 * @projectName com.youedata.modular.enums
 * @date 2020/01/10 13:20
 * @description:
 */
public enum AiType {
    /**
     * 0:pdf
     */
    PDF("0", "pdf"),
    /**
     * 1:img
     */
    IMG("1", "img"),
    /**
     * 2:ner
     */
    NER("2", "ner");

    private String code;
    private String desc;

    AiType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static AiType getByCode(String code) {
        AiType[] values = values();
        for (AiType sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        AiType[] values = values();
        for (AiType sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
