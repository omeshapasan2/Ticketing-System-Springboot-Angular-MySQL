package com.oop.Springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Configures a ThreadPoolTaskExecutor to manage the execution of asynchronous tasks.
 *
 * The ThreadPoolTaskExecutor provides a thread pool for executing tasks concurrently.
 * This configuration sets up the executor with a core pool size, max pool size,
 * queue capacity, and a thread name prefix for easier identification of threads.
 */
@Configuration
public class TaskExecutorConfig {

    /**
     * Configures and returns a ThreadPoolTaskExecutor for executing tasks asynchronously.
     *
     * @return a configured ThreadPoolTaskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);  // Set the core pool size to 10 threads
        executor.setMaxPoolSize(20);   // Set the maximum pool size to 20 threads
        executor.setQueueCapacity(50); // Set the task queue capacity to 50 tasks
        executor.setThreadNamePrefix("TicketingProcess-"); // Set a prefix for thread names
        executor.initialize(); // Initialize the executor
        return executor; // Return the configured executor
    }
}
