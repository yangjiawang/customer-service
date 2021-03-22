package com.dehe.config;

import com.dehe.Interceptor.LoginInterceptor;

import com.dehe.utils.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author YANGJIAWANG
 * @DATE 2020/12/4
 * @TIME 17:14
 **/
@Configuration
public class InterceptorConfig  implements WebMvcConfigurer{
    @Autowired
    RedisClient redisClient;
    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor(redisClient);
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor()).addPathPatterns("/admin/selectfrom")
                //不拦截哪些路径
                .excludePathPatterns("/admin/login.do");

        WebMvcConfigurer.super.addInterceptors(registry);

    }
}
