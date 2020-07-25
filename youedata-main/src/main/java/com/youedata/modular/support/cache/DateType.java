package com.youedata.modular.support.cache;


import com.youedata.modular.support.TimeProcessFactory;

import java.lang.annotation.*;

/**
 * 标注时间类型
 *
 * @author qun.zheng
 * @create 2018/1/12
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DateType {
    TimeProcessFactory.TimeGrain value() default TimeProcessFactory.TimeGrain.Day;
}