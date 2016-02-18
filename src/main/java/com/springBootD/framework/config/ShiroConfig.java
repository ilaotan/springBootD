package com.springBootD.framework.config;

import com.springBootD.framework.shiro.ShiroDbRealm;
import com.springBootD.framework.shiro.SystemAuthenticationFilter;
import com.springBootD.framework.shiro.SystemLogoutFilter;
import com.springBootD.framework.shiro.SystemPermissionsAuthorizationFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tan on 2016/2/17.
 */
@Configuration
public class ShiroConfig{


    /**
     * 缓存管理器
     */
    @Bean(name = "shiroEhcacheManager")
    public EhCacheManager shiroEhcacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

    /**
     * 凭证匹配器
     */
    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
        hcm.setHashAlgorithmName("md5");
        hcm.setHashIterations(1);
        hcm.setStoredCredentialsHexEncoded(true);
        return hcm;
    }

    /**
     * Realm实现
     */
    @Bean(name = "shiroDbRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroDbRealm shiroDbRealm() {
        ShiroDbRealm sdr = new ShiroDbRealm();

        //设置凭证匹配器
        sdr.setCredentialsMatcher(credentialsMatcher());
        sdr.setCachingEnabled(false);

        //// 登录认证的缓存
        //sdr.setAuthenticationCachingEnabled(true);
        //sdr.setAuthenticationCacheName("myRealm.authorizationCache");

        //菜单的权限缓存
        sdr.setAuthorizationCachingEnabled(true);
        sdr.setAuthorizationCacheName("myRealm.authorizationCache");

        return sdr;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 数据库认证的实现
        securityManager.setRealm(shiroDbRealm());

        // session管理器
        securityManager.setSessionManager(sessionManager());

        // 缓存管理器
        securityManager.setCacheManager(shiroEhcacheManager());

        return securityManager;
    }

    /**
     * session管理器
     */
    @Bean(name = "sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        // 超时时间
        sessionManager.setGlobalSessionTimeout(1800000);

        // session存储的实现
        sessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());

        SimpleCookie sessionIdCookie = new SimpleCookie("SHAREJSESSIONID");
        sessionIdCookie.setPath("/");
        // sessionIdCookie的实现,用于重写覆盖容器默认的JSESSIONID
        sessionManager.setSessionIdCookie(sessionIdCookie);

        // 定时检查失效的session
        sessionManager.setSessionValidationSchedulerEnabled(true);

        return sessionManager;
    }

    /**
     * 单机session
     */
    @Bean(name = "shiroCacheManager")
    public MemoryConstrainedCacheManager shiroCacheManager(){
        return new MemoryConstrainedCacheManager();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        shiroFilter.setSecurityManager(securityManager());

        shiroFilter.setLoginUrl("/system/login");
        shiroFilter.setSuccessUrl("/system/admin");
        shiroFilter.setUnauthorizedUrl("/login/unauth");

        Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();
        filterChainDefinitionMapping.put("/resource/**", "anon");
        filterChainDefinitionMapping.put("/captcha.svl", "anon");
        filterChainDefinitionMapping.put("/login/unauth", "anon");
        filterChainDefinitionMapping.put("/anon/**", "anon");
        filterChainDefinitionMapping.put("/system/login", "authc");
        filterChainDefinitionMapping.put("/system/druid/**", "authc");
        filterChainDefinitionMapping.put("/logout", "logout");
        filterChainDefinitionMapping.put("/system/login", "authc");
        filterChainDefinitionMapping.put("/system/**", "systemPermissions");
        filterChainDefinitionMapping.put("/front/**", "anon");
        filterChainDefinitionMapping.put("/**", "anon");
        //filterChainDefinitionMapping.put("/**", "systemPermissions");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);

        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("authc",new SystemAuthenticationFilter(shiroEhcacheManager()));
        filters.put("systemPermissions",new SystemPermissionsAuthorizationFilter());
        filters.put("logout",new SystemLogoutFilter());
        shiroFilter.setFilters(filters);

        return shiroFilter;
    }


    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * AOP式方法级权限检查
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }
}