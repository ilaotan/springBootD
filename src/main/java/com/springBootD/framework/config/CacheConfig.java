package com.springBootD.framework.config;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//配置应用的cacheManager，不与shiro-web.xml中的cacheManager重名而冲突。
@Configuration
@EnableCaching
@EnableAutoConfiguration
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {

		Cache cache = new ConcurrentMapCache("byUsername");

		SimpleCacheManager manager = new SimpleCacheManager();
		manager.setCaches(Arrays.asList(cache));

		return manager;
	}
}
