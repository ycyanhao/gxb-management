package com.youedata.config.datasource;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.roses.core.metadata.CustomMetaObjectHandler;
import com.youedata.base.auth.context.LoginContextHolder;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Date;

/**
 * mp的插件拓展和资源扫描
 *
 * @author hao.yan
 * @date 2019/12/20 20:33
 */
@Configuration
@MapperScan(basePackages = {
        "com.youedata.**.mapper"
})
public class PluginsConfig {

    @Bean
    public CustomMetaObjectHandler myMpFieldHandler() {
        return new CustomMetaObjectHandler() {

            @Override
            public void insertFill(MetaObject metaObject) {
            }

            @Override
            public void updateFill(MetaObject metaObject) {
            }

            @Override
            protected Long getUserUniqueId() {
                try {
                    return LoginContextHolder.getContext().getUser().getId();
                } catch (Exception e) {
                    //如果获取不到当前用户就存空id
                    return null;
                }
            }
        };
    }

}
