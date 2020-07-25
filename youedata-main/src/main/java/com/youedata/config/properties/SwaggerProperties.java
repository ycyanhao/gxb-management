package com.youedata.config.properties;

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

	/**
	 * api是否开启
	 */
    private boolean apiOpen = true;

	public boolean getApiOpen() {
		return apiOpen;
	}

	public void setApiOpen(boolean apiOpen) {
		this.apiOpen = apiOpen;
	}

}
