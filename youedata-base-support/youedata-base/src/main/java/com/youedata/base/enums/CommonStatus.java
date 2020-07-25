package com.youedata.base.enums;

import lombok.Getter;

/**
 * 公共状态
 *
 * @author hao.yan
 */
@Getter
public enum CommonStatus {

    ENABLE("ENABLE", "启用"),
    DISABLE("DISABLE", "禁用"),
    YES("Y","是"),
    NO("N","否");

    private String code;
    private String message;

    CommonStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getDescription(String status) {
        if (status == null) {
            return this.message;
        } else {
            for (CommonStatus s : CommonStatus.values()) {
                if (s.getCode().equals(status)) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
