package com.springBootD.framework.async;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 一些类描述信息.
 *
 * @author tan liansheng
 * @version 3.0.0
 */

@Configuration
public class ThreadConfiguration {

    @Value("${spring.task.pool.corePoolSize}")
    private int corePoolSize;

    @Value("${spring.task.pool.maxPoolSize}")
    private int maxPoolSize;

    @Value("${spring.task.pool.queueCapacity}")
    private int queueCapacity;

    @Value("${spring.task.pool.KeepAliveSeconds}")
    private int keepAliveSeconds;

    public ThreadConfiguration() {
    }

    /**
     * My task async pool executor.
     *
      @Async("myTaskAsyncPool") //pool名字跟ThreadConfiguration里的一致
      public void runGyNotify(final int num) {
     *
     *
     * @return the executor
     */
    @Bean
    public Executor myTaskAsyncPool() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(this.corePoolSize);
        executor.setMaxPoolSize(this.maxPoolSize);
        executor.setQueueCapacity(this.queueCapacity);
        executor.setKeepAliveSeconds(this.keepAliveSeconds);
        executor.setThreadNamePrefix("MyExecutor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
