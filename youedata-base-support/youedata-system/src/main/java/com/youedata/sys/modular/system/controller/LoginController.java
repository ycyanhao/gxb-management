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
package com.youedata.sys.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import com.google.code.kaptcha.Constants;
import com.sun.jna.platform.win32.WinNT;
import com.youedata.base.auth.context.LoginContextHolder;
import com.youedata.base.auth.exception.enums.AuthExceptionEnum;
import com.youedata.base.auth.service.AuthService;
import com.youedata.base.consts.ConstantsContext;
import com.youedata.base.tips.DCResponse;
import com.youedata.sys.core.auth.cache.SessionManager;
import com.youedata.sys.core.exception.InvalidKaptchaException;
import com.youedata.sys.modular.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
@Api(tags = "登录控制器")
public class LoginController extends BaseController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    /**
     * 跳转到主页
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:41 PM
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        //判断用户是否登录
        if (LoginContextHolder.getContext().hasLogin()) {
            Map<String, Object> userIndexInfo = userService.getUserIndexInfo();

            //用户信息为空，提示账号没分配角色登录不进去
            if (userIndexInfo == null) {
                model.addAttribute("tips", "该用户没有角色，无法登陆");
                return "/login.html";
            } else {
                model.addAllAttributes(userIndexInfo);
                return "/index.html";
            }

        } else {
            return "/login.html";
        }
    }

    /**
     * 跳转到登录页面
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:41 PM
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (LoginContextHolder.getContext().hasLogin()) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }

    /**
     * 点击登录执行的动作
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseData loginVali(String username, String password ,String kaptcha ) {
//
//        if (ToolUtil.isOneEmpty(username, password)) {
//            throw new RequestEmptyException("账号或密码为空！");
//        }
//
//        //验证验证码是否正确
//        if (ConstantsContext.getKaptchaOpen()) {
//            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//            if (ToolUtil.isEmpty(kaptcha.trim()) || !kaptcha.equalsIgnoreCase(code)) {
//                throw new InvalidKaptchaException();
//            }
//        }
//
//        //登录并创建token
//        String token = authService.login(username, password);
//
//        return new SuccessResponseData(token);
//    }

    /**
     * 点击登录执行的动作(测试)
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "String", defaultValue = "admin"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String", defaultValue = "111111"),
    })
    public DCResponse<String> loginVali(@RequestParam("username") String username, @RequestParam("password") String password ,  String kaptcha ) {

        if (ToolUtil.isOneEmpty(username, password)) {
            throw new RequestEmptyException("账号或密码为空！");
        }

        //验证验证码是否正确
        if (ConstantsContext.getKaptchaOpen()) {
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (StringUtils.equals(kaptcha,null) || ToolUtil.isEmpty(kaptcha.trim()) || !kaptcha.equalsIgnoreCase(code)) {
//                throw new InvalidKaptchaException();
                return DCResponse.error(AuthExceptionEnum.VALID_CODE_ERROR.getCode(),AuthExceptionEnum.VALID_CODE_ERROR.getMessage());
            }
        }

        //登录并创建token
        String token = authService.login(username, password);

        return DCResponse.ok(token);
    }

//    /**
//     * 退出登录
//     *
//     * @author fengshuonan
//     * @Date 2018/12/23 5:42 PM
//     */
//    @RequestMapping(value = "/logout")
//    @ResponseBody
//    public ResponseData logOut() {
//        authService.logout();
//        return new SuccessResponseData();
//    }

    /**
     * 退出登录
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("登出")
    public DCResponse<Void> logOut() {
        authService.logout();
        return DCResponse.ok(null);
    }

}