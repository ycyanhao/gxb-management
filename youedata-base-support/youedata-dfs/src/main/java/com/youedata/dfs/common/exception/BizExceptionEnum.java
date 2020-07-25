package com.youedata.dfs.common.exception;

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
    SUBJECT_WEBSITE_NUM_ERROR(101,"主体序号参数异常"),
    WEBSITE_NOT_EXIST(102,"查询不到站点信息"),

    /**
     * 文件流异常行
     */
    STREAM_ERROR(201,"流转化错误"),
    SERVER_ERROR(500, "服务器异常"),
    FILE_MAX_LIMIT(500, "文件太大!"),

    /**
     * token异常
     */
    TOKEN_EXPIRED(700, "token过期"),
    TOKEN_ERROR(700, "token验证失败"),
    AUTH_NO_ACCESS(700, "无访问权限"),
    AD_ERROR(700, "AD域认证异常"),

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
    SUBJECT_WEBSITE_NUM_NOT_EXSIT(1003,"主体站点不存在"),
    UN_PASS_REPEAT(1004,"不需要重复的不合格操作"),

    /**
     * 文件异常
     */
    FILE_EMPTY(2000,"文件不能为空"),
    FILE_UPLOAD_FAILED_RESULT_NOT_FOUND(2001, "主体站点数据已失效，可能做了重新爬取动作"),
    FILE_ID_NOT_EMPTY(2002, "文件id不能为空"),

    /**
     * 网络核查-- 配置清单异常
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
	EXCEL_UEL_ERROR(2115,"此文件无法导入"),
    CRAWLER_DATA_NOT_EXIST(2116,"当前爬取数据不存在"),
    IMPORT_ERROR(2117,"请按照模板规范填写后上传"),
    EXCEL_TEMPETE_WEB_ERROR(2118,"网站参数解析错误"),
    EXCEL_TEMPETE_WEB_PARAM_ERROR(2119,"网站参数解析错误"),
    EXCEL_NULL_ERROR(2120,"文件地址或在线填写的主体不能为空"),
    BATCH_NULL_ERROR(2121,"当前批次不存在"),
    
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
    INTERVIEW_NAME_NULL(2264,"导入访谈姓名不能为空"),
    INTERVIEW_POSITIN_NULL(2265,"导入访谈中职务不能为空"),
    INTERVIEW_IDCRAD_NULL(2266,"导入访谈中身份证不能为空"),
    INTERVIEW_PHONE_NULL(2267,"导入访谈中联系方式不能为空"),
    INTERVIEW_ADDRESS_NULL(2268,"导入访谈中访谈地点不能为空"),
    INTERVIEW_TIME_NULL(2269,"导入访谈中访谈时间不能为空"),
    INTERVIEW_JGNAME_NULL(2270,"导入访谈中监管部门名称不能为空"),
    INTERVIEW_SFRNAME_NULL(2271,"导入访谈中受访人姓名不能为空"),
    INTERVIEW_SFRPOSITIN_NULL(2272,"导入访谈中受访人职务不能为空"),
    INTERVIEW_NAMEN_NULL(2273,"导入访谈中名称不能为空"),
    INTERVIEW_PART_NULL(2274,"导入访谈中业务伙伴类型不能为空"),
    INTERVIEW_STASF_NULL(2275,"导入访谈中参访人员不能为空"),
    INTERVIEW_MANAGER_ILLEGAL(2276,"此工作单元非管理层访谈"),
    INTERVIEW_BUSINESS_PARTNERS_ILLEGAL(2277,"此工作单元非业务伙伴访谈"),
    INTERVIEW_SUPERVISE_ILLEGAL(2278,"此工作单元非监管部门访谈"),
    CHECK_PLAN_NAME_NULL(2279,"查验计划中核查手段不能为空"),
    CHECK_PLAN_SORT_NULL(2280,"查验计划中核查序号不能为空"),

    EXCEL_IMPORT_NULL(2276,"请按照模板规范填写后上传"),
    /**
     * 合规证明
     */
    EXCEL_IMPORT_NAME_NULL(2300,"主体名称不能为空"),
    EXCEL_IMPORT_TYPE_NULL(2301,"类型不能为空"),
    EXCEL_IMPORT_NO_NULL(2308,"序号不能为空"),
    EXCEL_IMPORT_TYPE_ERROR(2302,"类型错误"),
    PROOF_NULL(2303,"当前合规证明不存在"),
    PROOF_UPLOAD_NO(2304,"当前状态不可上传附件"),
    PROOF_UPLOAD_FIRDT(2305,"请先上传证明"),
    PROOF_DATE_FIRDT(2306,"覆盖时间出错"),
    PROOF_ILLEGAL(2307,"此工作单元非合规证明"),
    NO_MUST_NUMBER(2308,"序号值必须为数字"),
    
    
    /***
     * 询证
     * 
     */
    INQURY_NULL(2320,"当前的询证不存在"),
    INQURY_ILLEGAL(2321,"此工作单元非询证"),
    INQURY_HAS_PASS(2322,"当前询证计划未通过，不可上传"),
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
    PROJECT_INVITECODE_ERROR(3011, "邀请码错误"),

    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(400, "账号密码错误"),
    NO_ACCESS(403,"没有访问权限"),
    INTERNAL_EXCEPTION(500,"系统内部异常"),
	
	/**
	 * 前期文件
	 */
	PROPHASE_FILE_NOT_EMPTY(5001, "当前文件参数不能为空"),

    /**
     * 过程文件
     */
    MARK_VALUE_ILLEGAL(6000,"是否标记参数不合法"),
    UN_PASS_NEED_REMARK(6001,"审核不通过，理由不能为空"),
    PROCESS_FILE_TITLE_NAME_REPEAT(6002,"过程文件题目名称重复"),

    /**
     * 字典
     */
    DICT_EXISTED(400, "字典已经存在"),
    ERROR_CREATE_DICT(500, "创建字典失败"),
    ERROR_WRAPPER_FIELD(500, "包装字典属性失败"),
    ERROR_CODE_EMPTY(500, "字典类型不能为空"), 
    
    /**
     * 短信验证码
     */
    SMS_CODE_ERROR(7001, "短信验证错误"),
    
	/**
	 * 登录注册
	 */
    TWO_PWD_NOT_MATCH(7002, "两次密码不一致"),
	LOGIN_ERROR(7003, "用户名或密码错误"),
	USERNAME_REGEX_ERROR(7004, "用户名格式错误"),
	USER_HAS_EXISTS(7005, "用户名格式错误"),
	USER_NOT_EXISTS(7006, "用户不存在"),
	FORGET_PWD_VERIFY_ERROR(7007, "找回密码验证失败"),
	ROLE_NOT_EXISTS(7008, "角色信息获取异常"),
	PHONE_NOT_EXISTS(7009, "手机号不存在"),
	PASSWORD_ATTACHCODE_ERROR(7010, "附加码信息错误"),
	
	/**
     * 工作单元-自定义
     */
    CUSTOMWORKUNIT_NO(7101,"无匹配数据"),
    CUSTOMWORKUNIT_STATUS_NOT_MATCH(7102,"当前状态不可操作"),
    
    /**
     * 解压文件
     */
    UN_FILE_NOT_FOUND(7103,"压缩文件地址不能为空"),
    UN_FILE_DOWNLOAD_FILEED(7104,"压缩文件下载失败"),
    UN_FILE_NOT_SUPPORT(7105,"压缩文件格式不支持"),
    UN_FILE_OPERATE_FAILED(7106,"压缩文件解压失败"),
    UN_FILE_NO_SUB_FILES(7107,"解压目录中无子文件"),
    UN_FILE_UPLOAD_FAILED(7108,"压缩文件中的子文件上传失败"),
    UN_FILE_RAR_NOT_SUPPORT(7109,"不支持rar5版本"),
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

    @Override
    public String getMessage() {
        return message;
    }

}