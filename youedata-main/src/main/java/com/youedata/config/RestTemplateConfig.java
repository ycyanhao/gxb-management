package com.youedata.config;
/**
 * @title: RestTemplateConfig
 * @projectName com.youedata.config
 * @author TC
 * @version 1.0<br>
 * @date 2020/01/04 10:54
 * @description:
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName RestTemplateConfig
 * @Description TODO
 * @Author TC
 * @Date 2020/1/4 10:54
 * @Version 1.0
 **/
@Configuration
public class RestTemplateConfig {
    @Bean
    RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000);
        requestFactory.setReadTimeout(30000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
}
