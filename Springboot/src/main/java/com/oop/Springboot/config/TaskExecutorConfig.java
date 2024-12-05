package com.oop.Springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfig {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);  // Adjust as needed
        executor.setMaxPoolSize(20);   // Adjust as needed
        executor.setQueueCapacity(50); // Adjust as needed
        executor.setThreadNamePrefix("TicketingProcess-");
        executor.initialize();
        return executor;
    }
}
