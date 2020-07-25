package com.youedata.dfs.persistence.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;


/**
 * 文件存储记录 实体
 * @author liujijun
 *
 */
@TableName("file_store_log")
public class FileStoreLog extends Model<FileStoreLog> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 653170704561069710L;

	/**
    * id
    */
	@TableField("id")
    private String id;
	
	/**
	 * 使用项目
	 */
	@TableField("project")
	private String project;
	
	/**
	 * 业务类型
	 */
	@TableField("type")
	private String type;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;
    
    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;
    
    /**
     * 描述
     */
    @TableField("description")
    private String description;
    
    /**
    * 创建用户
    */
    @TableField("create_user")
    private String createUser;
    
    /**
    * 创建时间
    */
    @TableField("create_time")
    private Date createTime;

    /**
    * 更新用户
    */
    @TableField("update_user")
    private String updateUser;

    /**
    * 更新时间
    */
    @TableField("update_time")
    private Date updateTime;

    /**
    * 逻辑删除键
    */
    @TableField("del_flag")
    private String delFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public String toString() {
		return "FileStoreLog [id=" + id + ", project=" + project + ", type=" + type + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", description=" + description + ", createUser=" + createUser
				+ ", createTime=" + createTime + ", updateUser=" + updateUser + ", updateTime=" + updateTime
				+ ", delFlag=" + delFlag + "]";
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}
    
}