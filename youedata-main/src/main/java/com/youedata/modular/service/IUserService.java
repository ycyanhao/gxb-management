package com.youedata.modular.service;

/**
 * @Description 用户服务类
 * @Author zhangzb
 * @Date 2020/1/4
 */
public interface IUserService {

    /**
     * 创建user token
     * @param params
     * @return
     */
    String createUserToken(String params);
}
