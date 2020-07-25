package com.youedata;

import cn.stylefeng.roses.core.config.MybatisDataSourceAutoConfiguration;
import cn.stylefeng.roses.core.config.WebAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * SpringBoot方式启动类
 *
 * @author hao.yan
 */
@Slf4j
@ServletComponentScan
@SpringBootApplication(exclude = {WebAutoConfiguration.class, MybatisDataSourceAutoConfiguration.class})
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        log.info("{} start success!",SystemApplication.class.getSimpleName());
    }

}
