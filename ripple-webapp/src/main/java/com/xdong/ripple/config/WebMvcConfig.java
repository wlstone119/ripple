package com.xdong.ripple.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xdong.ripple.handler.RippleExceptionHandler;
import com.xdong.ripple.interceptor.AuthInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private VelocityViewResolver velocityViewResolver;

    @Autowired
    private AuthInterceptor      authInterceptor;

    @Bean
    public VelocityConfigurer velocityConfigurer() {
        Properties properties = new Properties();
        properties.setProperty("input.encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");
        VelocityConfigurer configurer = new VelocityConfigurer();
        configurer.setResourceLoaderPath("/WEB-INF/view/");
        configurer.setVelocityProperties(properties);
        return configurer;
    }

    @Bean
    public VelocityLayoutViewResolver velocityLayoutViewResolver() {
        VelocityLayoutViewResolver resolver = new VelocityLayoutViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".vm");
        resolver.setContentType(MediaType.TEXT_HTML_VALUE + ";charset=utf-8");
        resolver.setExposeSpringMacroHelpers(true);
        resolver.setExposeRequestAttributes(true);
        resolver.setRequestContextAttribute("rc");
        resolver.setDateToolAttribute("dateTool");
        resolver.setNumberToolAttribute("numberTool");
        resolver.setLayoutKey("/layout/crawler.vm");
        resolver.setLayoutUrl("/layout/crawler.vm");
        resolver.setScreenContentKey("screen_content");
        resolver.setOrder(-9999);
        return resolver;
    }

    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {
        return new CommonsMultipartResolver();
    }

    /**
     * MessageConverter配置
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = new ObjectMapper();
        // objectMapper.setPropertyNamingStrategy(new JsonResultNamingStrategy());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_HTML);
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(mediaTypes);
        converters.add(converter);
    }

    /**
     * 返回类型
     *
     * @param configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).favorParameter(false).ignoreAcceptHeader(true).mediaType("json",
                                                                                                     MediaType.APPLICATION_JSON_UTF8).mediaType("html",
                                                                                                                                                MediaType.TEXT_HTML).mediaType("xml",
                                                                                                                                                                               MediaType.APPLICATION_XML);
    }

    /**
     * 静态资源处理
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 视图解析器
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(velocityViewResolver);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ErrorPageFilter errorPageFilter() {
        return new ErrorPageFilter();
    }

    @Bean
    public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }

    /**
     * 异常拦截器
     *
     * @param exceptionResolvers
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new RippleExceptionHandler());
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    /**
     * 拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
    }

}
