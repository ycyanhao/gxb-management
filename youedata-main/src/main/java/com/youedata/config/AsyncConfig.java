//package com.youedata.config;
///**
// * @title: AsyncConfig
// * @projectName com.youedata.config
// * @author TC
// * @version 1.0<br>
// * @date 2019/12/27 9:24
// * @description:
// */
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//
//import java.util.concurrent.Executor;
//
///**
// * @ClassName AsyncConfig
// * @Description TODO
// * @Author TC
// * @Date 2019/12/27 9:24
// * @Version 1.0
// **/
//@Configuration
//@EnableAsync
//public class AsyncConfig {
//    @Bean("taskExecutor")
//    public Executor taskExecutor() {
//        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
//        executor.setPoolSize(20);
//        executor.setThreadNamePrefix("taskExecutor-");
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        executor.setAwaitTerminationSeconds(60);
//        return executor;
//    }
//}
