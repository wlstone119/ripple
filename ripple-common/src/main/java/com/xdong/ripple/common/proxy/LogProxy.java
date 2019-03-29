package com.xdong.ripple.common.proxy;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("rawtypes")
public class LogProxy {

    public static Logger getLogger(final Class<?> clazz) {
        String className = clazz.getName();
        return wrapperProxy(LogManager.getLogger(className), className);
    }

    private static Logger wrapperProxy(Logger logger, String className) {
        Class[] argumentTypes = new Class[] { String.class };
        Object[] arguments = new Object[] { className };
        return (Logger) new LogProxyEnhancer().createProxy(logger, argumentTypes, arguments);
    }

    static class LogProxyEnhancer implements MethodInterceptor {

        // 要代理的真实对象
        private Object obj;

        public Object createProxy(Object target, Class[] argumentTypes, Object[] arguments) {
            this.obj = target;

            Enhancer enhancer = new Enhancer();
            // 设置代理目标
            enhancer.setSuperclass(this.obj.getClass());
            // 设置单一回调对象，在调用中拦截对目标方法的调用
            enhancer.setCallback(this);
            // 设置类加载器
            enhancer.setClassLoader(this.obj.getClass().getClassLoader());

            return enhancer.create(argumentTypes, arguments);
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

            Object result = null;

            try {
                // 前置通知
                before();
                result = proxy.invokeSuper(obj, args);
                // 后置通知
                after(method.getName(), args);
            } catch (Exception e) {
                // 异常通知
                exception();
            } finally {
                // 方法返回前通知
                beforeReturning();
            }

            return result;
        }

        private void beforeReturning() {

        }

        private void exception() {

        }

        private void after(String methodName, Object[] args) {
            if ("error".equals(methodName)) {
                
            }
        }

        private void before() {

        }
    }

    public static void main(String[] args) throws InterruptedException {
        LogProxy proxy = new LogProxy();
        for (int i = 0; i < 100000; i++) {
            Logger logger = proxy.getLogger(HashMap.class);
            System.out.println(logger + "===" + i);
            Thread.sleep(100);
        }
    }
}
