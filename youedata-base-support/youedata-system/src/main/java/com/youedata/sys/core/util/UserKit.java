package com.youedata.sys.core.util;

import com.youedata.base.exception.BusinessException;
import com.youedata.base.utils.HttpKit;
import com.youedata.modular.model.dto.UserInfoDTO;
import com.youedata.modular.model.vo.User;
import com.youedata.sys.core.exception.enums.BizMyExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 用户信息KIT
 * @Author zhangzb
 * @Date 2020/1/4
 */
@Slf4j
@Component
public class UserKit {

    @Value("${aes.key}")
    private String aesKey;

    @Autowired
    private RedisUtil redisUtil;

    public static String USERTOKEN = "userToken";

    /**
     * 解析用户信息
     *
     * @param params
     * @return
     */
    public UserInfoDTO getUserInfoDTO(String params) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        String plain;
        try {
//            plain = RSAUtils.decrypt(params,privateKey);
            plain = AESUtil.aesDecrypt(params, aesKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(BizMyExceptionEnum.USER_DECRYPT_FAILD);
        }
        try {
            String[] paramArr = plain.split("&");
            for (String p : paramArr) {
                String[] strings = p.split("=");
                String key = strings[0];
                String value = strings[1];

                if (StringUtils.equals("userid", key)) {
                    userInfoDTO.setUserid(value);
                } else if (StringUtils.equals("useridgroup", key)) {
                    userInfoDTO.setUseridgroup(value);
                } else if (StringUtils.equals("username", key)) {
                    userInfoDTO.setUsername(value);
                } else if (StringUtils.equals("timestamp", key)) {
                    userInfoDTO.setTimestamp(Long.valueOf(value));
                }
            }

            if (StringUtils.isBlank(userInfoDTO.getUserid())
                    || StringUtils.isBlank(userInfoDTO.getUseridgroup())
                    || StringUtils.isBlank(userInfoDTO.getUsername())
                    || userInfoDTO.getTimestamp() == null) {
                throw new BusinessException(BizMyExceptionEnum.USER_INVALID_PARAM);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(BizMyExceptionEnum.USER_INVALID_PARAM);
        }
        return userInfoDTO;
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public User getUser() {
        String token = getToken();
        if (StringUtils.isNotEmpty(token)) {
            String userStr = redisUtil.get(token);
            log.info("UserToken-{},【从Redis获取后】-userStr:{}",token, userStr);
            if (!StringUtils.isEmpty(userStr)) {
                User user = FastJsonUtils.getJsonToBean(userStr, User.class);
                log.info("UserToken-{},【将Redis的值转换后】-user:{}",token, user);
                return user;
            }
        }
        throw new BusinessException(BizMyExceptionEnum.TOKEN_EXPIRED_NOT_EXIST);
//        return new User();
    }

    /**
     * 获取当前用户的ID
     *
     * @return
     */
    public String getUserId() {
        if (null != getUser()) {
            return String.valueOf(getUser().getUserid());
        }
        return null;
    }

    /**
     * 获取当前用户的token
     */
    public String getToken() {
        if (null == HttpKit.getNativeRequest()) {
            return null;
        }
        final String requestHeader = HttpKit.getNativeRequest().getHeader(UserKit.USERTOKEN);
        log.info("requestHeader-{}", requestHeader);
        String authToken = null;
        if (requestHeader != null) {
            authToken = requestHeader;
        }
        log.info("authToken-{}", authToken);
        return authToken;
    }

}
