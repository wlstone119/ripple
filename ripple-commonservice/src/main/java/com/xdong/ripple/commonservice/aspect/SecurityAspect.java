package com.xdong.ripple.commonservice.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xdong.ripple.commonservice.annotation.DecryptSecurity;
import com.xdong.ripple.commonservice.annotation.EncryptSecurity;
import com.xdong.ripple.commonservice.util.ReflectionUtil;
import com.xdong.ripple.commonservice.util.SecurityUtil;
import com.xdong.ripple.dal.entity.system.RpSysUserDo;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Aspect
@Component
public class SecurityAspect {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    @Pointcut("@annotation(com.xdong.ripple.commonservice.annotation.EncryptSecurity)")
    public void encryptSecurityPointCut() {
    }

    @Pointcut("@annotation(com.xdong.ripple.commonservice.annotation.DecryptSecurity)")
    public void decryptSecurityPointCut() {
    }

    @Around("decryptSecurityPointCut()")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();

        String[] argNames = methodSignature.getParameterNames();
        Object[] args = point.getArgs();

        Method method = methodSignature.getMethod();
        DecryptSecurity security = method.getAnnotation(DecryptSecurity.class);
        String[] values = security.value();
        if (values != null && values.length >= 1) {
            for (String propertyName : values) {
                int idx = propertyName.indexOf(":");
                if (idx > 0) {
                    for (int i = 0; i < argNames.length; i++) {
                        String[] annoArgs = propertyName.split(":");
                        if (annoArgs == null || annoArgs.length != 2) {
                            continue;
                        }
                        String paramName = annoArgs[0];
                        if (paramName.equals(argNames[i])) {
                            String fields = annoArgs[1];
                            String[] fieldsArgs = fields.split(",");
                            decryptBean(fieldsArgs, args[i]);
                        }
                    }
                } else {
                    for (int i = 0; i < argNames.length; i++) {
                        if (propertyName.equals(argNames[i]) && args[i] instanceof String) {
                            args[i] = SecurityUtil.decryption((String) args[i]);
                        }
                    }
                }
            }
        }

        return point.proceed(args);
    }

    @AfterReturning(pointcut = "encryptSecurityPointCut()", returning = "returnValue")
    public void afterReturing(JoinPoint jp, Object returnValue) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        EncryptSecurity security = method.getAnnotation(EncryptSecurity.class);

        doSecurity(jp, returnValue, security);
    }

    public void doSecurity(JoinPoint jp, Object result, EncryptSecurity security) {
        if (result == null) {
            return;
        }

        String[] configs = security.value();
        if (configs == null || configs.length < 1) {
            return;
        }
        for (String config : configs) {
            if (StringUtils.isBlank(config)) {
                continue;
            }

            String[] fields = config.split(",");
            if (fields == null || fields.length < 1) {
                continue;
            }

            // 安全处理
            security(fields, result);
        }
    }

    private static void security(String[] fields, Object result) {
        if (result == null) {
            return;
        } else if (result instanceof List) {
            securityList(fields, result);
        } else if (result instanceof Map) {
            securityMap(fields, result);
        } else {
            securityBean(fields, result);
        }
    }

    private static void securityList(String[] fileds, Object result) {
        List lst = (List) result;
        for (Object obj : lst) {
            security(fileds, obj);
        }
    }

    private static void securityMap(String[] fields, Object result) {
        Map<String, Object> rs = (Map<String, Object>) result;
        for (String field : fields) {
            try {
                int idx = field.indexOf(".");
                if (idx > 0) {
                    // 带点，说明是子bean中的属性，需要迭代设置
                    String parentFiled = field.substring(0, idx);
                    String childField = field.substring(idx + 1);
                    Object parent = rs.get(parentFiled);
                    security(new String[] { childField }, parent);
                } else {
                    // 调用get方法获取result中该字段的值
                    Object value = rs.get(field);
                    if (value == null || !(value instanceof String)) {
                        continue;
                    }
                    // 根据type获取消隐后的值
                    String blankStr = SecurityUtil.encryption((String) value);
                    // 调用get方法设置值
                    rs.put(field, blankStr);
                }
            } catch (Exception e) {
                logger.error("返回对象安全处理失败, Field:" + field, e);
                continue;
            }
        }
    }

    private static void decrypt(String[] fields, Object result) {
        if (result == null) {
            return;
        }
        decryptBean(fields, result);
    }

    private static void decryptBean(String[] fields, Object result) {
        for (String field : fields) {
            try {
                int idx = field.indexOf(".");
                if (idx > 0) {
                    String parentFiled = field.substring(0, idx);
                    String childField = field.substring(idx + 1);
                    Object parent = ReflectionUtil.invokeGetter(result, parentFiled);
                    decrypt(new String[] { childField }, parent);
                } else {
                    Object value = ReflectionUtil.invokeGetter(result, field);
                    if (value == null || !(value instanceof String)) {
                        continue;
                    }
                    String blankStr = SecurityUtil.decryption((String) value);
                    ReflectionUtil.invokeSetter(result, field, blankStr);
                }
            } catch (Exception e) {
                logger.error("返回对象安全处理失败, Field:" + field, e);
                continue;
            }
        }
    }

    private static void securityBean(String[] fields, Object result) {
        for (String field : fields) {
            try {
                int idx = field.indexOf(".");
                if (idx > 0) {
                    String parentFiled = field.substring(0, idx);
                    String childField = field.substring(idx + 1);
                    Object parent = ReflectionUtil.invokeGetter(result, parentFiled);
                    security(new String[] { childField }, parent);
                } else {
                    Object value = ReflectionUtil.invokeGetter(result, field);
                    if (value == null || !(value instanceof String)) {
                        continue;
                    }
                    String blankStr = SecurityUtil.encryption((String) value);
                    ReflectionUtil.invokeSetter(result, field, blankStr);
                }
            } catch (Exception e) {
                logger.error("返回对象安全处理失败, Field:" + field, e);
                continue;
            }
        }
    }

    public static void main(String[] args) {

        String field = "avc.123.123";
        int idx = field.indexOf(".");

        String parentFiled = field.substring(0, idx);
        String childField = field.substring(idx + 1);

        System.out.println(parentFiled + "===" + childField);

        List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "qweqwe");
        lst.add(map);

        security(new String[] { "name" }, lst);
        System.out.println(lst.get(0).get("name"));

        List<RpSysUserDo> ac = new ArrayList<RpSysUserDo>();
        RpSysUserDo userDo = new RpSysUserDo();
        userDo.setUsername("47389259832759382");
        ac.add(userDo);
        map.put("bean", ac);
        security(new String[] { "bean.openaccountId" }, map);

        System.out.println(((RpSysUserDo) ((List) map.get("bean")).get(0)).getUsername());
    }

}
