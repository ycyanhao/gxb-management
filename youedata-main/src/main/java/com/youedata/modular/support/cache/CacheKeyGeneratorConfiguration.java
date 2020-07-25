package com.youedata.modular.support.cache;

import com.alibaba.fastjson.JSON;
import com.youedata.modular.support.TimeProcessFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @Description 缓存KEY生成器配置
 * @Author zhangzb
 * @Date 2020/1/7
 */
@Slf4j
@Configuration
public class CacheKeyGeneratorConfiguration {

    /**
     * 自定义key
     * @return
     */
    @Bean(name = "customKeyGenerator")
    public KeyGenerator customKeyGenerator() {
        return (target, method, params) -> generateKey(target,method, params);
    }

    public static String generateKey(Object target,Method method, Object... params){
        List<Object> list = new ArrayList<>(params.length + 1);
        list.add(target.getClass().getSimpleName());
        list.add(method.getName());
        if (params.length > 0) {
            Parameter[] parameters = method.getParameters();
            IntStream.range(0, params.length).forEach(index -> {
                Object param = params[index];
                if (param instanceof Date) {
                    Date date = (Date) param;
                    Parameter parameter = parameters[index];
                    DateType dateType = parameter.getAnnotation(DateType.class);
                    if (dateType != null) {
                        date = pickDate(date,dateType.value());
                        list.add(date);
                        list.add(dateType.value());
                        return;
                    }

                }
                list.add(param);

            });
        }
        String key = JSON.toJSONString(list);
        log.info("cache key:{}",key);
        return key;
    }

    private static Date pickDate(Date date, TimeProcessFactory.TimeGrain timeGrain) {
        TimeProcessFactory.TimeRegion timeRegion = TimeProcessFactory.processTime(date, timeGrain);
        return timeRegion.getStartTime().toDate();
    }

}
