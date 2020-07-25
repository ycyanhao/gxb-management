/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.youedata.exception;

import com.youedata.base.exception.ServiceExceptionEnum;

/**
 * @author fengshuonan
 * @Description 所有业务异常的枚举
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum implements ServiceExceptionEnum {


    /**
     * 通用
     */
    DATA_NOT_EXIST(1000, "数据不存在"),
    ERROR_JSONEXCEPTION(1001, "数据格式非法"),
    AUTH_ERROR(1002, "只能操作自己的数据"),
    CHRACTER_ERROR(1003, "数据编码转换错误"),
    PARAM_ERROR(1003, "参数错误"),


    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(400, "账号密码错误"),
    NO_ACCESS(403, "没有访问权限"),
    INTERNAL_EXCEPTION(500, "系统内部异常"),

    /**
     * 数据准备异常信息
     */

    IMPORT_NOT_EXIST(11000, "数据准备不存在"),
    IMPORT_PARAM_ERROR_COMPANY(11001, "被核对单位不能为空"),
    IMPORT_PARAM_ERROR_BANKID(11002, "开户行ID不能为空"),
    IMPORT_PARAM_ERROR_BANKACCOUNT(11003, "银行账户不能为空"),
    IMPORT_PARAM_ERROR_DATADATE(11004, "数据期间不能为空"),

    IMPORT_NOT_DELETE(11005, "数据已被任务引用，请先删除任务再删除数据"),
    IMPORT_DATE_ERRER(11006, "数据期间格式有误"),
    IMPORT_IMPORT_IS_ESXIT(11007, "数据期间不可与当前任务重叠"),
    IMPORT_FILE_BANK_ERROR(11008, "银行对账单必须是pdf文件"),
    IMPORT_FILE_COMPANY_ERROR(11009, "企业明细账必须是excel文件"),

    IMPORT_FILE_COMPANY_HEAD_ERROR(11020, "企业明细账未解析到表头"),
    IMPORT_AI_NOT_FINISH(11021, "请在数据识别完成后创建任务"),
    IMPORT_AI_FAIL(11022, "数据识别失败，不可创建新任务"),
    IMPORT_YEAR_NOT_NULL(11023, "核对年份不能为空"),
    IMPORT_HAVE_TASK(11024, "存在AI核对任务，不可操作"),


    /**
     * 银行基础数据
     */
    BANKINFO_NOT_EXIST(12000, "银行数据不存在"),


    /**
     * Ai核对
     */
    AI_CHECK_NOT_EXIST(13000, "AI核对数据不存在"),
    AI_TASK_DATE_OVER(13001, "核对期间不可与当前任务重叠"),
    AI_BALANCE_ERROR(13002, "生成发生额数据异常"),
    AI_TASK_NOT_EXIST(13003, "任务不存在"),

    /**
     * FASTDFS
     */
    FASTDFS_UPLOAD_ERROR(12001, "文件上传错误"),
    EXCEL_ERROR(12002, "文件异常"),
    EXCEL_DOWNLOAD_ERROR(12003, "文件下载失败"),
    EXCEL_DATA_ERROR(12004, "文件解析异常"),
    IMG_DATA_ERROR(12005, "图片信息不存在"),
    FILE_NOT_EXISTS(12006, "文件不存在"),
    /**
     * 银行对账单
     */
    BankStatement_Date_NULL(14000, "日期不能为空"),
    BankStatement_Balance_NULL(14001, "余额不能为空"),
    BankStatement_NOT_EXIST(14002, "对账单数据为空"),
    BankStatement_Debtor_ADN_Credit_ALL_NULL(14003, "对账单借方和贷方金额不能同时为空"),
    /**
     * AI请求
     */
    AI_REQUEST_ERROR(15000, "算法请求失败"),

    /**
     * 余额核对
     */
    BALANCE_CHECK_NOT_EXIST(17000, "余额核对数据不存在"),

    /**
     * 双向交易
     */

    CHECK_TWO_PARAM_ERROR(16000, "参数错误"),
    CHECK_TWO_NOT_EXIST(16001, "数据不存在"),

    /**
     * 银行基础数据
     */
    PATTERN_ALIAS_EXISTS(20001, "词库名称已存在"),

    /**
     * 用户
     */
    USER_DECRYPT_FAILD(30001, "数据加密通讯失败"),
    USER_INVALID_PARAM(30002, "数据内容不正确"),
    USER_DATA_EXPIRE(30002, "数据已过期"),


    /**
     * 企业基础数据
     */
    COMPANY_PARAM_ERROR(40001, "参数错误"),
    COMPANY_DETAIL_NOT_EXISTS(40002, "企业明细表数据不存在"),

    /**
     * 结果汇总
     */
    RESULT_NOT_EXSIT(50001, "结果汇总数据不存在"),
    ;

    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
