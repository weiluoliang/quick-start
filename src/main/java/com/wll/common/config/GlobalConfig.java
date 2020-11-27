package com.wll.common.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.wll.common.filter.SimpleCORSFilter;
import com.wll.common.interceptor.AuthInterceptor;
import com.wll.common.monitor.DingTalkMonitor;
import com.wll.common.monitor.Monitor;
import com.wll.common.uitl.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;
import java.util.List;


@Slf4j
@Configuration
public class GlobalConfig {

    @Value("${spring.profiles.active}")
    private String profile ;
    @Value("${monitor.pushUrl}")
    private String pushUrl;
    @Value("${monitor.appName}")
    private String appName;

    @Resource
    private AuthInterceptor authInterceptor;

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new SimpleCORSFilter());
        registration.addUrlPatterns("/*");
        registration.setName("cors");
        return registration;
    }

    @Bean
    public Monitor monitor(){
        return new DingTalkMonitor(appName, profile ,pushUrl);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
//        Set<String> excludeFields = Sets.newHashSet("orderByList","page","limit");
        return new WebMvcConfigurer() {

            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                FastJsonConfig fastJsonConfig = new FastJsonConfig();
                FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter() {
                    @Override
                    protected boolean supports(Class<?> clazz) {
                        return clazz != String.class && super.supports(clazz);
                    }
                };
                converter.setFastJsonConfig(fastJsonConfig);
                converters.add(0, converter);
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(authInterceptor)
                        .excludePathPatterns("/test/**")
                        .excludePathPatterns("/testTask/**")
                        .excludePathPatterns("/")
                        .excludePathPatterns("/error")
                        .addPathPatterns("/**");
            }
        };
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(20));//20MB
        factory.setMaxRequestSize(DataSize.ofMegabytes(20));//20MB
        return factory.createMultipartConfig();
    }

    @Bean
    public SpringUtil springUtil(){
        return new SpringUtil();
    }
}
