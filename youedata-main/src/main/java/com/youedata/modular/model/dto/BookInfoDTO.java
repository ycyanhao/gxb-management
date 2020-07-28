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
 * 教材管理
 * </p>
 *
 * @author hao.yan
 * @date 2020-07-28
 */
@Data
@ApiModel
public class BookInfoDTO implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("")
    private Long id;

    /**
     * 课程名称
     */
    @ApiModelProperty("课程名称")
    private String courseName;

    /**
     * 教材名称
     */
    @ApiModelProperty("教材名称")
    private String bookName;

    /**
     * 教材类型（00：出版教材；01：自编教材）
     */
    @ApiModelProperty("教材类型（00：出版教材；01：自编教材）")
    private String bookType;

    /**
     * 教材正面封面URL
     */
    @ApiModelProperty("教材正面封面URL")
    private String frontPicUrl;

    /**
     * 教材背面图片URL
     */
    @ApiModelProperty("教材背面图片URL")
    private String backPicUrl;

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
