package com.zzh.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    private Integer id;//编号
    private String image;//企业图片
    private String brandName;//品牌名称
    private String companyName;//企业名称
    private Integer ordered;//排序字段
    private String description;//描述
    private Integer status;//状态 0: 禁用 1:启用

    //逻辑视图
    public String getStatusStr() {
        if (status == null) {
            return "未知";
        }
        return status == 0 ? "禁用" : "启用";
    }
}