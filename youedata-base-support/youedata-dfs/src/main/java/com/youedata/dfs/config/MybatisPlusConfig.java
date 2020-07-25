package com.youedata.dfs.config;

import javax.annotation.PostConstruct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

/**
 * MybatisPlus配置
 *
 * @author stylefeng
 * @Date 2017年8月23日12:51:41
 */
@Configuration
@MapperScan(basePackages = {"com.youedata.dfs.**.dao"})
public class MybatisPlusConfig {
	
	@Autowired 
	private DatabaseInit databaseinit;
	
	/**
	 * 数据库初始化
	 */
	@PostConstruct
    public void init() {
		databaseinit.init();
	}
	
    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
