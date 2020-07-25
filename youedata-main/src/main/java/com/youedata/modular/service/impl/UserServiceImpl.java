package com.youedata.modular.service.impl;

import com.youedata.base.auth.jwt.JwtTokenUtil;
import com.youedata.base.exception.BusinessException;
import com.youedata.exception.BizExceptionEnum;
import com.youedata.modular.model.dto.UserInfoDTO;
import com.youedata.modular.service.IUserService;
import com.youedata.sys.core.util.FastJsonUtils;
import com.youedata.sys.core.util.RedisUtil;
import com.youedata.sys.core.util.UserKit;
import io.jsonwebtoken.impl.Base64UrlCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description 用户服务类实现类
 * @Author zhangzb
 * @Date 2020/1/4
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserKit userKit;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 创建user token
     *
     * @param params
     * @return
     */
    @Override
    public String createUserToken(String params) {
        // 解码，处理特殊字符
        byte[] decodeResult = Base64UrlCodec.BASE64URL.decode(params);
        params = new String(decodeResult);
        UserInfoDTO userInfoDTO = userKit.getUserInfoDTO(params);
        Map<String, Object> claims = new HashMap<>();
        claims.put("randomKey", jwtTokenUtil.getRandomKey());
        // 设置8小时有效期
        Long expTimestamp = userInfoDTO.getTimestamp()+8*60*60*1000;
        Date expDate = new Date(expTimestamp);
        String usertoken = jwtTokenUtil.generateToken(userInfoDTO.getUserid(), expDate,claims);
        long timeout=expTimestamp-System.currentTimeMillis();
        if(timeout<=0){
            throw new BusinessException(BizExceptionEnum.USER_DATA_EXPIRE);
        }
        redisUtil.setEx(usertoken, FastJsonUtils.getBeanToJson(userInfoDTO), timeout, TimeUnit.MILLISECONDS);
        return usertoken;
    }
}
