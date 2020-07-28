package com.youedata.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户信息", description = "用户信息")
public class User {
    @ApiModelProperty(value = "用户ID", name = "userId")
    private Long userId;
    @ApiModelProperty(value = "用户名称", name = "userName")
    private String userName;

}
