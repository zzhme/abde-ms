package com.zzh.aspect;

import com.zzh.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;


/**
 * 日志记录切面
 */

@Component
@Aspect
@Slf4j
public class LogAspect {
    private static final Logger operateLog = LoggerFactory.getLogger("OPERATE_LOG");

    @Around("@annotation(com.zzh.Log)")  //只要目标方法上含有Log注解,就会执行当前环绕通知标记的方法(增强方法/通知)
    public Object recordLog(ProceedingJoinPoint pjp) {
        try {
            //1.获取数据
            Integer loginUserId = ThreadLocalUtils.getCurrentId();
            String operateUser = ThreadLocalUtils.getUsername();
            LocalDateTime operateTime = LocalDateTime.now();
            String className = pjp.getTarget().getClass().getName();
            String methodName = pjp.getSignature().getName();
            Object[] args = pjp.getArgs();

            String methodParams = args == null ? "no parameters" : Arrays.toString(args);

            long begin = System.currentTimeMillis();
            Object result = pjp.proceed();
            long costTime = System.currentTimeMillis() - begin;

            String resultValue = result == null ? "no result" : result.toString();

            //2.创建日志对象
            //OperateLog operateLog = new OperateLog(loginUserId, operateUser, operateTime, className, methodName, methodParams, resultValue, costTime);

            // 用 SLF4J 写到日志文件，JSON 格式便于后续采集
            operateLog.info("user={}, method={}, params={}, result={}, cost={}ms",
                    operateUser, className + "." + methodName,
                    methodParams, resultValue, costTime);

            return result;

        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("记录日志失败！", e);
        }
    }
}