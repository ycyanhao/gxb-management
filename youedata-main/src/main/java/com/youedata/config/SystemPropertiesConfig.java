package com.youedata.config;

import com.youedata.sys.core.properties.SystemProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目中的配置
 *
 * @author hao.yan
 */
@Configuration
public class SystemPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = SystemProperties.PREFIX)
    public SystemProperties systemProperties() {
        return new SystemProperties();
    }


}
