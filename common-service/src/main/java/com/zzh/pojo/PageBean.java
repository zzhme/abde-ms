package com.zzh.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//封装分页查询结果
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageBean<T> {
    private Long totalCount;//总记录数
    private List<T> dataList;
}