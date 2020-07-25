package com.youedata.base.consts;

import cn.stylefeng.roses.core.util.ToolUtil;
import com.google.common.collect.Maps;
import com.youedata.base.enums.CommonStatus;
import com.youedata.base.sms.AliyunSmsProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Map;

import static cn.stylefeng.roses.core.util.ToolUtil.getTempPath;

/**
 * 系统常量的容器
 *
 * @author hao.yan
 */
@Slf4j
public class ConstantsContext {

    private static final String TIPS_END = "，若想忽略此提示，请在开发管理->系统配置->参数配置，设置相关参数！";

    /**
     * 所有的常量，可以增删改查
     */
    private static Map<String, Object> CONSTANTS_HOLDER = Maps.newConcurrentMap();
    /**
     * 添加系统常量
     */
    public static void putConstant(String key, Object value) {
        if (ToolUtil.isOneEmpty(key, value)) {
            return;
        }
        CONSTANTS_HOLDER.put(key, value);
    }

    /**
     * 删除常量
     */
    public static void deleteConstant(String key) {
        if (ToolUtil.isOneEmpty(key)) {
            return;
        }

        //如果是系统常量
        if (!key.startsWith(ConfigConstant.SYSTEM_CONSTANT_PREFIX)) {
            CONSTANTS_HOLDER.remove(key);
        }
    }

    /**
     * 获取系统常量
     */
    public static Map<String, Object> getConstntsMap() {
        return CONSTANTS_HOLDER;
    }

    /**
     * 获取验证码开关
     */
    public static Boolean getKaptchaOpen() {
        String gunsKaptchaOpen = (String) CONSTANTS_HOLDER.get("YOUEDATA_KAPTCHA_OPEN");
        if (CommonStatus.ENABLE.getCode().equalsIgnoreCase(gunsKaptchaOpen)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取短信的配置
     */
    public static AliyunSmsProperties getAliyunSmsProperties() {
        String gunsSmsAccesskeyId = (String) CONSTANTS_HOLDER.get("YOUEDATA_SMS_ACCESSKEY_ID");
        String gunsSmsAccesskeySecret = (String) CONSTANTS_HOLDER.get("YOUEDATA_SMS_ACCESSKEY_SECRET");
        String gunsSmsSignName = (String) CONSTANTS_HOLDER.get("YOUEDATA_SMS_SIGN_NAME");
        String gunsSmsLoginTemplateCode = (String) CONSTANTS_HOLDER.get("YOUEDATA_SMS_LOGIN_TEMPLATE_CODE");
        String gunsSmsInvalidateMinutes = (String) CONSTANTS_HOLDER.get("YOUEDATA_SMS_INVALIDATE_MINUTES");

        AliyunSmsProperties aliyunSmsProperties = new AliyunSmsProperties();
        aliyunSmsProperties.setAccessKeyId(gunsSmsAccesskeyId);
        aliyunSmsProperties.setAccessKeySecret(gunsSmsAccesskeySecret);
        aliyunSmsProperties.setSignName(gunsSmsSignName);
        aliyunSmsProperties.setLoginTemplateCode(gunsSmsLoginTemplateCode);
        aliyunSmsProperties.setInvalidateMinutes(Integer.valueOf(gunsSmsInvalidateMinutes));
        return aliyunSmsProperties;
    }

    /**
     * 获取管理系统名称
     */
    public static String getSystemName() {
        String systemName = (String) CONSTANTS_HOLDER.get("YOUEDATA_SYSTEM_NAME");
        if (ToolUtil.isEmpty(systemName)) {
            log.error("系统常量存在空值！常量名称：YOUEDATA_SYSTEM_NAME，采用默认名称：Guns快速开发平台" + TIPS_END);
            return "Guns快速开发平台";
        } else {
            return systemName;
        }
    }

    /**
     * 获取管理系统名称
     */
    public static String getDefaultPassword() {
        String defaultPassword = (String) CONSTANTS_HOLDER.get("YOUEDATA_DEFAULT_PASSWORD");
        if (ToolUtil.isEmpty(defaultPassword)) {
            log.error("系统常量存在空值！常量名称：YOUEDATA_DEFAULT_PASSWORD，采用默认密码：111111" + TIPS_END);
            return "111111";
        } else {
            return defaultPassword;
        }
    }

    /**
     * 获取管理系统名称
     */
    public static String getOAuth2UserPrefix() {
        String oauth2Prefix = (String) CONSTANTS_HOLDER.get("YOUEDATA_OAUTH2_PREFIX");
        if (ToolUtil.isEmpty(oauth2Prefix)) {
            log.error("系统常量存在空值！常量名称：YOUEDATA_OAUTH2_PREFIX，采用默认值：oauth2" + TIPS_END);
            return "oauth2";
        } else {
            return oauth2Prefix;
        }
    }

    /**
     * 获取顶部导航条是否开启
     */
    public static Boolean getDefaultAdvert() {
        String gunsDefaultAdvert = (String) CONSTANTS_HOLDER.get("YOUEDATA_DEFAULT_ADVERT");
        if (ToolUtil.isEmpty(gunsDefaultAdvert)) {
            log.error("系统常量存在空值！常量名称：YOUEDATA_DEFAULT_ADVERT，采用默认值：false" + TIPS_END);
            return false;
        } else {
            return CommonStatus.ENABLE.getCode().equalsIgnoreCase(gunsDefaultAdvert);
        }
    }

    /**
     * 获取系统发布的版本号（防止css和js的缓存）
     */
    public static String getReleaseVersion() {
        String systemReleaseVersion = (String) CONSTANTS_HOLDER.get("YOUEDATA_SYSTEM_RELEASE_VERSION");
        if (ToolUtil.isEmpty(systemReleaseVersion)) {
            log.error("系统常量存在空值！常量名称：YOUEDATA_SYSTEM_RELEASE_VERSION，采用默认值：system" + TIPS_END);
            return ToolUtil.getRandomString(8);
        } else {
            return systemReleaseVersion;
        }
    }

    /**
     * 获取文件上传路径(用于头像和富文本编辑器)
     */
    public static String getFileUploadPath() {
        String gunsFileUploadPath = (String) CONSTANTS_HOLDER.get("YOUEDATA_FILE_UPLOAD_PATH");
        if (ToolUtil.isEmpty(gunsFileUploadPath)) {
            log.error("系统常量存在空值！常量名称：YOUEDATA_FILE_UPLOAD_PATH，采用默认值：系统tmp目录" + TIPS_END);
            return getTempPath();
        } else {
            //判断有没有结尾符
            if (!gunsFileUploadPath.endsWith(File.separator)) {
                gunsFileUploadPath = gunsFileUploadPath + File.separator;
            }

            //判断目录存不存在
            File file = new File(gunsFileUploadPath);
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                if (mkdirs) {
                    return gunsFileUploadPath;
                } else {
                    log.error("系统常量存在空值！常量名称：YOUEDATA_FILE_UPLOAD_PATH，采用默认值：系统tmp目录" + TIPS_END);
                    return getTempPath();
                }
            } else {
                return gunsFileUploadPath;
            }
        }
    }

    /**
     * 用于存放bpmn文件
     */
    public static String getBpmnFileUploadPath() {
        String bpmnFileUploadPath = (String) CONSTANTS_HOLDER.get("YOUEDATA_BPMN_FILE_UPLOAD_PATH");
        if (ToolUtil.isEmpty(bpmnFileUploadPath)) {
            log.error("系统常量存在空值！常量名称：YOUEDATA_BPMN_FILE_UPLOAD_PATH，采用默认值：系统tmp目录" + TIPS_END);
            return getTempPath();
        } else {
            //判断有没有结尾符
            if (!bpmnFileUploadPath.endsWith(File.separator)) {
                bpmnFileUploadPath = bpmnFileUploadPath + File.separator;
            }

            //判断目录存不存在
            File file = new File(bpmnFileUploadPath);
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                if (mkdirs) {
                    return bpmnFileUploadPath;
                } else {
                    log.error("系统常量存在空值！常量名称：YOUEDATA_BPMN_FILE_UPLOAD_PATH，采用默认值：系统tmp目录" + TIPS_END);
                    return getTempPath();
                }
            } else {
                return bpmnFileUploadPath;
            }
        }
    }

    /**
     * 获取系统地密钥
     */
    public static String getJwtSecret() {
        String systemReleaseVersion = (String) CONSTANTS_HOLDER.get("YOUEDATA_JWT_SECRET");
        log.info("JwtSecret值为:{}",systemReleaseVersion);
        if (ToolUtil.isEmpty(systemReleaseVersion)) {
            String randomSecret = ToolUtil.getRandomString(32);
            CONSTANTS_HOLDER.put("YOUEDATA_JWT_SECRET", randomSecret);
            log.error("jwt密钥存在空值！常量名称：YOUEDATA_JWT_SECRET，采用默认值：随机字符串->" + randomSecret + TIPS_END);
            return randomSecret;
        } else {
            return systemReleaseVersion;
        }
    }

    /**
     * 获取系统地密钥过期时间（单位：秒）
     */
    public static Long getJwtSecretExpireSec() {
        Long defaultSecs = 86400L;
        String systemReleaseVersion = (String) CONSTANTS_HOLDER.get("YOUEDATA_JWT_SECRET_EXPIRE");
        if (ToolUtil.isEmpty(systemReleaseVersion)) {
            log.error("jwt密钥存在空值！常量名称：YOUEDATA_JWT_SECRET_EXPIRE，采用默认值：1天" + TIPS_END);
            CONSTANTS_HOLDER.put("YOUEDATA_JWT_SECRET_EXPIRE", String.valueOf(defaultSecs));
            return defaultSecs;
        } else {
            try {
                return Long.valueOf(systemReleaseVersion);
            } catch (NumberFormatException e) {
                log.error("jwt密钥过期时间不是数字！常量名称：YOUEDATA_JWT_SECRET_EXPIRE，采用默认值：1天" + TIPS_END);
                CONSTANTS_HOLDER.put("YOUEDATA_JWT_SECRET_EXPIRE", String.valueOf(defaultSecs));
                return defaultSecs;
            }
        }
    }

    /**
     * 获取token的header标识
     */
    public static String getTokenHeaderName() {
        String tokenHeaderName = (String) CONSTANTS_HOLDER.get("YOUEDATA_TOKEN_HEADER_NAME");
        if (ToolUtil.isEmpty(tokenHeaderName)) {
            String defaultName = "Authorization";
            CONSTANTS_HOLDER.put("YOUEDATA_TOKEN_HEADER_NAME", defaultName);
            log.error("获取token的header标识为空！常量名称：YOUEDATA_TOKEN_HEADER_NAME，采用默认值：" + defaultName + TIPS_END);
            return defaultName;
        } else {
            return tokenHeaderName;
        }
    }

}
