package com.youedata.modular.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 教材管理
 * </p>
 *
 * @author hao.yan
 * @since 2020-07-28
 */
@TableName("book_info")
public class BookInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 课程名称
     */
    @TableField("course_name")
    private String courseName;

    /**
     * 教材名称
     */
    @TableField("book_name")
    private String bookName;

    /**
     * 教材类型（00：出版教材；01：自编教材）
     */
    @TableField("book_type")
    private String bookType;

    /**
     * 教材正面封面URL
     */
    @TableField("front_pic_url")
    private String frontPicUrl;

    /**
     * 教材背面图片URL
     */
    @TableField("back_pic_url")
    private String backPicUrl;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField("create_user")
    private Long createUser;

    /**
     * 修改人
     */
    @TableField("update_user")
    private Long updateUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getFrontPicUrl() {
        return frontPicUrl;
    }

    public void setFrontPicUrl(String frontPicUrl) {
        this.frontPicUrl = frontPicUrl;
    }

    public String getBackPicUrl() {
        return backPicUrl;
    }

    public void setBackPicUrl(String backPicUrl) {
        this.backPicUrl = backPicUrl;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
        "id=" + id +
        ", courseName=" + courseName +
        ", bookName=" + bookName +
        ", bookType=" + bookType +
        ", frontPicUrl=" + frontPicUrl +
        ", backPicUrl=" + backPicUrl +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", createUser=" + createUser +
        ", updateUser=" + updateUser +
        "}";
    }
}
