package com.youedata.sys.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 分组表
 * </p>
 *
 * @author stylefeng
 * @since 2019-12-27
 */
@Data
@TableName("sys_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 组名
     */
    @TableField("name")
    private String name;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建者id
     */
    @TableField("create_user_id")
    private String createUserId;

    /**
     * 创建者名称
     */
    @TableField("create_user_name")
    private String createUserName;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新者id
     */
    @TableField("update_user_id")
    private String updateUserId;

    /**
     * 更新者名称
     */
    @TableField("update_user_name")
    private String updateUserName;

    /**
     * 是否删除：N-未删除，Y-已删除
     */
    @TableField("del_flag")
    private String delFlag;

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", createUserId='" + createUserId + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", updateTime=" + updateTime +
                ", updateUserId='" + updateUserId + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
