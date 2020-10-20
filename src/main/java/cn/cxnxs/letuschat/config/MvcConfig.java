package cn.cxnxs.letuschat.config;

import cn.cxnxs.letuschat.interceptor.PermissionInterceptor;
import cn.cxnxs.letuschat.interceptor.ResponseResultInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p></p>
 *
 * @author mengjinyuan
 * @date 2020-10-14 10:26
 **/
@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private ResponseResultInterceptor responseResultInterceptor;

    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("------设置全局权限拦截器------");
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/system/**")
                .excludePathPatterns("/chat/**");

        log.info("------设置统一接口拦截器------");
        registry.addInterceptor(responseResultInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/static/**");
    }
}
