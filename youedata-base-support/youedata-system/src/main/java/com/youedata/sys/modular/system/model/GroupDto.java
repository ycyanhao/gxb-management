package com.youedata.sys.modular.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分组dto
 * @author wyw
 * @Date 2019/12/27 10:24
 */
@Data
public class GroupDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     * 组名
     */
    @ApiModelProperty("组名")
    private String name;
}
