package com.xdong.ripple.commonservice.aspect;

import java.lang.reflect.Method;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xdong.ripple.common.utils.HttpContextUtils;
import com.xdong.ripple.common.utils.IPUtils;
import com.xdong.ripple.commonservice.annotation.Log;
import com.xdong.ripple.dal.entity.system.RpSysLogDo;
import com.xdong.ripple.spi.system.IRpSysLogService;

@Aspect
@Component
public class LogAspect {
    
    private final Logger        logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IRpSysLogService            rpSysLogServiceImpl;

    @Pointcut("@annotation(com.xdong.ripple.commonservice.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        long beginTime = System.currentTimeMillis();

        // 执行方法
        Object result = point.proceed();

        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        // 异步保存日志
        saveLog(point, time);

        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RpSysLogDo sysLog = new RpSysLogDo();
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSON.toJSONString(args);
            sysLog.setParams(params);
        } catch (Exception e) {
            logger.error("获取参数异常", e);
        }

        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        
        // 用户名
        
        sysLog.setTime((int) time);

        // 系统当前时间
        sysLog.setGmtCreate(new Date());

        // 保存系统日志
        rpSysLogServiceImpl.insert(sysLog);
    }
}
