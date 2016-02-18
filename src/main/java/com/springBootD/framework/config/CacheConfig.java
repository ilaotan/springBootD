package com.springBootD.framework.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


//配置应用的cacheManager，不与shiro-web.xml中的cacheManager重名而冲突。
@Configuration
@EnableCaching
public class CacheConfig {

	/*
 * 据shared与否的设置,Spring分别通过CacheManager.create()或new CacheManager()方式来创建一个ehcache基地.
 */
	@Bean
	public EhCacheManagerFactoryBean cacheManagerFactory(){
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
		cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache.xml"));
		cacheManagerFactoryBean.setShared (true);
		return cacheManagerFactoryBean;
	}

	/*
  * ehcache 主要的管理器
  */
	@Bean(name = "cacheManager")
	public EhCacheCacheManager ehCacheCacheManager(){
		return new EhCacheCacheManager (cacheManagerFactory().getObject ());
	}

}
