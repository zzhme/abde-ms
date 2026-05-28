package com.zzh.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class TimeAspect {

    @Around("execution(* com.zzh..impl.*.*(..))")
    public Object recordTime(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();

        //执行原方法
        Object result = pjp.proceed();

        long end = System.currentTimeMillis();
        log.info("执行耗时：{} ms", (end - begin));

        return result;
    }
}
