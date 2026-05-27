package com.zzh;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//表示只能使用在方法上
@Target(ElementType.METHOD)
//表示运行时期可以获取
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
}
