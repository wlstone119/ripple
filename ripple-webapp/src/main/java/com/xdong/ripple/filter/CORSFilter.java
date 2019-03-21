package com.xdong.ripple.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CORSFilter implements Filter {

    @SuppressWarnings("serial")
    private final Set<String> ALLOWED_DOMAINS = new HashSet<String>() {

        {
            add(".365xdong.cn");
            add("127.0.0.1");
        }
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String allowedDomains = filterConfig.getInitParameter("allowedDomains");
        if (allowedDomains != null) {
            ALLOWED_DOMAINS.addAll(Arrays.asList(allowedDomains.split(",")));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                              ServletException {
        if (allowCors(request)) {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            // servletResponse.addHeader("Access-Control-Allow-Origin", "*");
            servletResponse.setHeader("Access-Control-Allow-Origin", servletRequest.getHeader("Origin"));// 必填
            servletResponse.setHeader("Access-Control-Allow-Methods",
                                      servletRequest.getHeader("Access-Control-Request-Method"));// 可选
            servletResponse.setHeader("Access-Control-Allow-Headers",
                                      servletRequest.getHeader("Access-Control-Request-Headers"));// 可选
            servletResponse.setHeader("Access-Control-Allow-Credentials", "true");// 可选
            servletResponse.setHeader("Access-Control-Max-Age", getCorsMaxAge(request));// 可选，指定本次预检请求的有效期，单位为秒,我先写个1天
        }
        chain.doFilter(request, response);
    }

    // 可继承改写，根据自己情况设置哪些源地址、目标url允许跨域
    protected boolean allowCors(ServletRequest request) {
        String originDomain = getHostName(((HttpServletRequest) request).getHeader("Origin"));
        if (originDomain != null) {
            for (String domain : ALLOWED_DOMAINS) {
                if (originDomain.endsWith(domain)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private String getHostName(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        try {
            return new URL(url).getHost();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    // 可继承改写，自己设置有效期
    protected String getCorsMaxAge(ServletRequest request) {
        return "86400";
    }

    @Override
    public void destroy() {
    }
}
