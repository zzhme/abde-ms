package com.zzh.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//封装分页查询条件
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpPageQuery {
    private String name;//姓名
    private Integer gender;//性别
    //jackson工具中的注解: 指定json串解析成日期LocalDate对象时的格式
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;//入职日期 起始日期 区域查询
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;//入职日期 结束日期 区域查询
    private Integer currentPage;;//当前页码
    private Integer pageSize;//每页显示记录数
    private Integer startIndex;//当前页面数据的起始索引
}