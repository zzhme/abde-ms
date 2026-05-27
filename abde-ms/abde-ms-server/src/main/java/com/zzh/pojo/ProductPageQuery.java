package com.zzh.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 产品分页与条件查询
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPageQuery {
    /** 产品名称（模糊） */
    private String name;
    /** 最低价格（含） */
    private BigDecimal minPrice;
    /** 最高价格（含） */
    private BigDecimal maxPrice;
    /** 状态：1 上架 0 下架，null 表示不限 */
    private Integer status;
    private Integer currentPage;
    private Integer pageSize;
}
