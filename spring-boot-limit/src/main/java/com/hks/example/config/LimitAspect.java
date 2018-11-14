package com.hks.example.config;

import com.hks.example.annotation.RateLimit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;


/**
 * 描述:拦截器
 *
 * @author yanpenglei
 * @create 2018-08-16 15:33
 **/
@Aspect
@Configuration
public class LimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(LimitAspect.class);

    @Autowired
    private RedisTemplate<String, Serializable> limitRedisTemplate;

    @Autowired
    private DefaultRedisScript<Number> redisluaScript;

    @Around("execution(* com.hks.example.controller ..*(..) )")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        if (rateLimit != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ipAddress = getIpAddr(request);

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(ipAddress).append("-")
                    .append(targetClass.getName()).append("- ")
                    .append(method.getName()).append("-")
                    .append(rateLimit.key());

            List<String> keys = Collections.singletonList(stringBuffer.toString());

            Number number = limitRedisTemplate.execute(redisluaScript, keys, rateLimit.count(), rateLimit.time());

            if (number != null && number.intValue() != 0 && number.intValue() <= rateLimit.count()) {
                logger.info("限流时间段内访问第：{} 次", number.toString());
                return joinPoint.proceed();
            }

        } else {
            return joinPoint.proceed();
        }

        throw new RuntimeException("已经到设置限流次数");
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }
}
