package com.zzh.aspect;

import com.zzh.pojo.Emp;
import com.zzh.pojo.OperateLog;
import com.zzh.service.EmpService;
import com.zzh.service.LogService;
import com.zzh.utils.ThreadLocalUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 日志记录切面
 */
@Component
@Aspect
public class LogAspect {
    @Autowired
    EmpService empService;
    @Autowired
    LogService logService;

    /**
     * 只要目标方法上含有Log注解,就会执行当前环绕通知标记的方法(增强方法/通知)
     *
     * @param pjp
     */
    @Around("@annotation(com.zzh.Log)")
    public Object recordLog(ProceedingJoinPoint pjp) {
        try {
            //1.获取数据
            Integer loginUserId = ThreadLocalUtils.getCurrentId();
            Emp emp = empService.selectById(loginUserId);
            String operateUser = emp.getUsername();
            LocalDateTime operateTime = LocalDateTime.now();
            String className = pjp.getTarget().getClass().getName();
            String methodName = pjp.getSignature().getName();
            Object[] args = pjp.getArgs();

            String methodParams = args == null ? "no parameters" : Arrays.toString(args);

            long begin = System.currentTimeMillis();
            Object result = pjp.proceed();
            long costTime = begin - System.currentTimeMillis();

            String resultValue = result == null ? "no result" : result.toString();

            //2.创建日志对象
            OperateLog operateLog = new OperateLog(loginUserId, operateUser, operateTime, className, methodName, methodParams, resultValue, costTime);

            //3.插入日志
            logService.insert(operateLog);

            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("记录日志失败！！！");
        }


    }
}
