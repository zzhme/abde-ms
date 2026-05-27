package com.zzh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//统一响应结果实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;//1代表成功,0代表失败
    private String msg;//success代表成功消息,error代表失败消息
    private Object data;//响应的具体数据

    //静态方法:针对查询成功的操作(响应状态码对应的消息是固定success)
    public static Result success(Object data) {
        return new Result(1, "success", data);
    }

    //静态方法:针对查询成功的操作(自定义响应状态码对应的消息)
    public static Result success(String msg, Object data) {
        return new Result(1, msg, data);
    }

    //静态方法:针对增删改成功的操作(响应状态码对应的消息是固定success)
    public static Result success() {
        return new Result(1, "success", null);
    }

    //静态方法:针对增删改成功的操作(响应状态码对应的消息是固定success)
    public static Result success(String msg) {
        return new Result(1, msg, null);
    }

    //静态方法:针对增删改查失败的操作(响应状态码对应的消息是固定error)
    public static Result error() {
        return new Result(0, "error", null);
    }

    //静态方法:针对增删改查失败的操作(自定义响应状态码对应的消息)
    public static Result error(String msg) {
        return new Result(0, msg, null);
    }
}
