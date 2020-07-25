package com.youedata.dfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * swagger相关配置
 *
 * @author 刘基军
 */
@Configuration
@ConfigurationProperties(prefix = SwaggerProperties.SWAGGER_PREFIX)
public class SwaggerProperties {

    public static final String SWAGGER_PREFIX = "swagger";

    private boolean apiOpen = true;//api是否开启

	public boolean getApiOpen() {
		return apiOpen;
	}

	public void setApiOpen(boolean apiOpen) {
		this.apiOpen = apiOpen;
	}

}
