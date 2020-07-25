package com.youedata.dfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 日志相关配置
 *
 * @author liujijun
 */
@Configuration
@ConfigurationProperties(prefix = FileLogProperties.FILE_LOG_PREFIX)
public class FileLogProperties {

    public static final String FILE_LOG_PREFIX = "file";

    private boolean dblogOpen = false;
    private boolean deleteOpen = false;

	public boolean isDblogOpen() {
		return dblogOpen;
	}

	public void setDblogOpen(boolean dblogOpen) {
		this.dblogOpen = dblogOpen;
	}

	public boolean isDeleteOpen() {
		return deleteOpen;
	}

	public void setDeleteOpen(boolean deleteOpen) {
		this.deleteOpen = deleteOpen;
	}
}
