package com.youedata.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 用户信息
 * @Author zhangzb
 * @Date 2020/1/4
 */
@Data
@ApiModel(value = "用户信息",description = "用户信息")
public class User {
    @ApiModelProperty(value = "用户ID",name = "userid")
    private String userid;
    @ApiModelProperty(value = "用户名称",name = "username")
    private String username;
    @ApiModelProperty(value = "用户组",name = "useridgroup")
    private String useridgroup;

}
