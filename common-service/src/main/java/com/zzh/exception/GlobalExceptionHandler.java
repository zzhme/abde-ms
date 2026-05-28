package com.zzh.exception;

import com.zzh.pojo.Emp;
import com.zzh.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 作用: 拦截所有的异常进行处理,特殊异常特殊处理,其它异常统一处理
 * RestControllerAdvice注解 = ControllerAdvice注解 + ResponseBody注解
 * ResponseBody注解: 把控制器方法返回对象解析成json
 * ControllerAdvice注解: 拦截/获取所有的异常对象(AOP思想:把代理的思想又做了进一步的封装)
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    //统一异常处理方法
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error("非常抱歉,您的网络出现故障,请稍后重试!!!");
    }

    @ExceptionHandler(DeptException.class)
    public Result handleDeptException(DeptException e) {
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(EmpException.class)
    public Result handleEmpException(EmpException e) {
        e.printStackTrace();
        return Result.error(e.getMessage());
    }
}
