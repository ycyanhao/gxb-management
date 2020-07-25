package com.youedata.modular.controller;

import com.youedata.base.tips.DCResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youedata.sys.core.util.UserHolder;

/**
 * @Description 用户信息控制器
 * @Author zhangzb
 * @Date 2020/1/4
 */
@RestController
@Api(value = "/user", tags = "用户信息")
@RequestMapping("/user")
public class UserController {


    @ApiOperation(value = "获取当前用户信息", notes = "获取当前用户信息")
    @GetMapping("/current-user")
    public DCResponse usertoken() {
        return DCResponse.ok(UserHolder.getContext().getUser());
    }

}
