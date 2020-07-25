package com.youedata.sys.core.util;

import cn.stylefeng.roses.core.util.SpringContextHolder;
import com.youedata.modular.model.vo.User;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Description 用户上下文处理对象
 * @Author zhangzb
 * @Date 2020/1/4
 */
public class UserHolder {

    public static UserKit getContext() {
        return SpringContextHolder.getBean(UserKit.class);
    }

    /**
     * 用户信息
     *
     * @return
     */
    public static User getUser() {
        return getContext().getUser();
/*         紫夜寒风

        User user = new User();
        user.setUserid("yanhaoceshi");
        user.setUsername("紫夜寒风");
        user.setUseridgroup("yanhaoceshi");
        return user;
*/

//        管理员
//        User user = new User();
//        user.setUserid("6393f14a530b455fb824a8abb4ba8fbd");
//        user.setUsername("admin");
//        user.setUseridgroup("all");
//        return user;
    }

    /**
     * 用户ID
     *
     * @return
     */
    public static String getUserId() {
        if (null != getUser()) {
            return getUser().getUserid();
        }
        return null;
    }

    /**
     * 用户名称
     *
     * @return
     */
    public static String getUserName() {
        if (null != getUser()) {
            return getUser().getUsername();
        }
        return null;
    }

    /**
     * 用户组 添加了all为空的处理
     *
     * @return
     */
    public static String getUseridgroup() {
        if (null != getUser()) {
            String useridgroup = getUser().getUseridgroup();
            return StringUtils.isBlank(useridgroup) || StringUtils.equals("ALL", useridgroup.toUpperCase()) ? null : useridgroup;
        }
        return null;
    }

    public static List<String> getGroupUserIds() {
        if (null != getUseridgroup()) {
            return Arrays.asList(getUseridgroup().split(","));
        }
        return null;
    }
}
