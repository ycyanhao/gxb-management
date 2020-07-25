package com.youedata.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 临时目录 相关配置
 *
 * @author liujijun
 */
@Configuration
@ConfigurationProperties(prefix = TempProperties.REST_PREFIX)
public class TempProperties {

    static final String REST_PREFIX = "temp";

	/**
	 * 临时文件处理路径
	 */
	private String unfilePath;

	public String getUnFilePath() {
		return unfilePath;
	}

	public void setUnFilePath(String unfilePath) {
		this.unfilePath = unfilePath;
	}
}
