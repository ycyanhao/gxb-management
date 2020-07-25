package com.youedata.modular.enums;

public enum FileType {
    /**
     *  文件类型
     */
    BANK_PDF("1", "银行对账单PDF"),
    BANK_IMG("2", "银行对账单图片"),
    COMPANY_EXCEL("3", "企业对账单excel");

    private String code;
    private String desc;

    FileType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static FileType getByCode(String code) {
        FileType[] values = values();
        for (FileType sw : values) {
            if (sw.code.equals(code)) {
                return sw;
            }
        }
        return null;
    }


    public static Boolean checkCode(String code) {
        FileType[] values = values();
        for (FileType sw : values) {
            if (sw.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
