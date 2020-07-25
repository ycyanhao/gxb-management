package com.youedata.dfs.common;

import com.stylefeng.guns.core.exception.ServiceExceptionEnum;

/**
 * 所有业务异常的枚举
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum implements ServiceExceptionEnum {

    /**
     * 通用异常
     */
    PARAMS_NOT_EXIST(100,"参数不能为空"),

    /**
     * token异常
     */
    TOKEN_EXPIRED(700, "token过期"),
    TOKEN_ERROR(700, "token验证失败"),

    /**
     * 签名异常
     */
    SIGN_ERROR(700, "签名验证失败"),

    /**
     * 网络核查-主体
     */
    SUBJECT_NO_MORE(1000,"没有更多的主体信息"),
    SUBJECT_REPEAT(1001,"存在重复主体信息"),
    SUBJECT_NOT_EXSIT(1002,"主体不存在"),

    /**
     * 文件异常
     */
    FILE_EMPTY(2000,"文件不能为空"),
    FILE_UPLOAD_FAILED_RESULT_NOT_FOUND(2001, "主体站点数据已失效，可能做了重新爬取动作"),
    FILE_ID_NOT_EMPTY(2002, "文件id不能为空"),

    /**
     * 网络核查-- 配置请打异常
     */
	CRAWLER_NO_DELETE(2100,"当前状态不可以删除"),
	CRAWLER_NO_HAVE(2101,"当前抓取批次号已不存在"),
	PROJECT_NO_HAVE(2103,"当前案件不存在"),
	PROJECT_HAVA_CRAWLER(2104,"当前案件已存在此爬取任务"),
	WEBSITE_NO_HAVE(2105,"当前的站点不存在"),
	WEBSITE_CONFIG_EMPTY(2106,"网站配置参数不能为空"),
	WEBSITE_CONFIG_ERROR(2107,"网站配置值错误"),
	SUBJECT_IMPORT_ERROR(2108,"导入的数量为0，导入失败"),
	WEBSITES_TEMPETE_ERROR(2109,"站点格式输入有误"),
	EXCEL_TEMPETE_ERROR(2110,"上传格式有误"),
	SUBJECT_TEMPETE_ERROR(2111,"主体参数输入有误"),
	PARAM_TEMPETE_ERROR_NO(2112,"序号值不能为空"),
	PARAM_TEMPETE_ERROR_NAME(2113,"核查主体不能为空"),
	PARAM_TEMPETE_ERROR_NO_COPY(2114,"序号值重复"),
	EXCEL_UEL_ERROR(2115,"文件地址有误"),
    CRAWLER_DATA_NOT_EXIST(2116,"当前爬取数据不存在"),
    
    /**
     * 事项相关
     */
    MATTER_HAS_NO(2150, "当前事项不存在"),
    MATTER_STATUS_HAS_CHANGE(2151, "当前事项状态已改变"),
    
    
    /**
     * 访谈相关
     */
    WORK_UNIT_HAS_NO(2250, "当前案件下的工作单元不存在"),
    INTERVIEW_HAS_NO(2251, "当前访谈不存在"),
    INTERVIEW_DONOT_DELETE(2252, "当前状态下不可删除"),
    OPERATION_DONOT(2253, "当前操作异常"),
    INTERVIEW_ERROR_COMMIT(2254, "当前状态不可提交，提交失败"),
    INTERVIEW_HAVE_NO_COMMIT(2255, "还未提交，审核失败"),
    INTERVIEW_NOPASS_REMARK(2256, "不合格，理由必填"),
    INTERVIEW_EMPTY(2257, "参数为空"),
    UPLOAD_FILE_CANNT(2258, "当前状态不可以上传提纲"),
    FILE_HAVE_DELETE(2259,"当前文件已删除"),
    INTERVIEW_HAS_PASS(2260,"当前访谈计划未通过，不可上传"),
    INTERVIEW_CANNT_UPLOAD(2261,"当前状态不可上传"),
    INTERVIEW_EXECUTE_NO(2262,"当前访谈计划未通过，不可提交"),
    INTERVIEW_AUDIT_HAVE(2263,"已审核过了，审核失败"),
    
    
    /**
     * 案件相关
     */
    PROJECT_CHARGE_LAWYER_NOT_FOUND(3000,"案件未指定负责律师信息"),
    PROJECT_COMPLETE_ALREADY(3001, "案件处于完成状态"),
    PROJECT_NOT_HAS_MATTER(3002, "案件下没有事项信息"),
    PROJECT_HAS_PROCESS_MATTER(3003, "案件下有正在进行中的事项"),
    PROJECTNO_CAN_NOT_EMPTY(3004,"案件号不能为空"),
    PROJECT_PROCESSING_ALREADY(3005,"案件处于进行中状态"),
    PROJECT_NO_AUTH(3006, "当前没有操作此案件的权限"),
    PROJECT_EXEC_LAWYER_NOT_FOUND(3007, "案件未指定执行律师信息"),
    PROJECT_PROJECT_TYPE_NOT_FOUND(3008, "案件未指定项目类型"),
    PROJECT_MATTER_NOT_FOUND(3009, "案件未指定事项信息"),
    PROJECT_WORKUNIT_NOT_FOUND(3010, "事项未指定工作单元信息"),

    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(400, "账号密码错误"),
	
	
	/**
	 * 前期文件
	 */
	PROPHASE_FILE_NOT_EMPTY(5001, "当前文件参数不能为空"),

    /**
     * 过程文件
     */
    MARK_VALUE_ILLEGAL(6000,"是否标记参数不合法"),
    UN_PASS_NEED_REMARK(6001,"审核不通过，理由不能为空"),
    PROCESS_FILE_TITLE_NAME_REPEAT(6002,"过程文件题目名称重复");


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

    @Override
    public String getMessage() {
        return message;
    }

}