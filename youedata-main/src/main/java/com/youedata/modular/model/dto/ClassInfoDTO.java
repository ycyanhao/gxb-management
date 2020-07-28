package com.youedata.modular.model.dto;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 班级信息
 * </p>
 *
 * @author hao.yan
 * @date 2020-07-28
 */
@Data
@ApiModel
public class ClassInfoDTO implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 班级主键
     */
    @ApiModelProperty("班级主键")
    private Long id;

    /**
     * 班级编号:项目部名称首拼大写字母+培训课程名称(等级）+日期
     */
    @ApiModelProperty("班级编号:项目部名称首拼大写字母+培训课程名称(等级）+日期")
    private String classNo;

    /**
     * 课程名称
     */
    @ApiModelProperty("课程名称")
    private String courseName;

    /**
     * 培训等级（00：初级；01：中级；02：高级）
     */
    @ApiModelProperty("培训等级（00：初级；01：中级；02：高级）")
    private String trainLevel;

    /**
     * 培训形式（00：线上；01：线下；02：线上+线下）
     */
    @ApiModelProperty("培训形式（00：线上；01：线下；02：线上+线下）")
    private String trainForm;

    /**
     * 培训地点
     */
    @ApiModelProperty("培训地点")
    private String trainAddress;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long createUser;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private Long updateUser;

    @Override
    public String checkParam() {
        return null;
    }

}
