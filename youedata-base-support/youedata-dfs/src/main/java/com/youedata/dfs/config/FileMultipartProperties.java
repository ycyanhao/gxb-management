package com.youedata.dfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置相关配置
 *
 * @author liujijun
 */
@Configuration
@ConfigurationProperties(prefix = FileMultipartProperties.REST_PREFIX)
public class FileMultipartProperties {

    public static final String REST_PREFIX = "file.multipart";

	/**
	 * 文件上传地址
	 */
	private String uploadtype;

	public String getUploadtype() {
		return uploadtype;
	}

	public void setUploadtype(String uploadtype) {
		this.uploadtype = uploadtype;
	}

	
}
