package com.xdong.ripple.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xdong.ripple.filter.CORSFilter;

@SuppressWarnings({ "serial" })
@Configuration
public class ServletConfig {

    // 需要处理跨域的请求
    public final Set<String> needCors = new HashSet<String>() {

        {
            add("/*");
        }
    };

    @Bean
    public FilterRegistrationBean webLogTraceFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CORSFilter());
        return registration;
    }
}
