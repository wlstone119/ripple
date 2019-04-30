package com.xdong.ripple.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class WebExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(WebExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj,
                                         Exception e) {
        StringBuilder urlBulider = new StringBuilder();
        if (obj instanceof HandlerMethod) {// 因为我们配置的是RequestMappingHandlerMapping所以大部分都覆盖了
            RequestMapping classAnnontion = AnnotationUtils.getAnnotation(((HandlerMethod) obj).getBeanType(),
                                                                          RequestMapping.class);
            RequestMapping methodAnnontion = AnnotationUtils.getAnnotation(((HandlerMethod) obj).getMethod(),
                                                                           RequestMapping.class);
            String[] subUrl = null;
            if (methodAnnontion != null) {
                subUrl = methodAnnontion.value();
            }
            String[] prefixUrl = null;
            if (classAnnontion != null) {
                prefixUrl = classAnnontion.value();
            }
            if (prefixUrl != null) {
                urlBulider.append(StringUtils.join(prefixUrl, ";")).append("/");
            }
            if (subUrl != null) {
                urlBulider.append(StringUtils.join(subUrl, ";"));
            }
        } else {// 只记录了处理器的名字，不准确
            urlBulider.append(obj.getClass().getName());
        }

        ModelAndView result = new ModelAndView("jsonView");
        return result;
    }

}
