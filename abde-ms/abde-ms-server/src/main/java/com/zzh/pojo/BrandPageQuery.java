package com.zzh.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandPageQuery {
    private Integer status;
    private String companyName;
    private String brandName;
    private Integer pageNum;
    private Integer pageSize;
    private Integer startIndex;
}
