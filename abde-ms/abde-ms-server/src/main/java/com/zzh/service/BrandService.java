package com.zzh.service;

import com.zzh.pojo.Brand;
import com.zzh.pojo.BrandPageQuery;
import com.zzh.pojo.PageBean;

import java.util.List;

public interface BrandService {
    /**
     * 多条件分页查询
     */
    PageBean<Brand> selectByCondition(BrandPageQuery brandPageQuery, Integer pageNum, Integer pageSize);

    /**
     * 根据id查询
     */
    Brand selectById(Integer id);

    /**
     * 根据brandName查询
     */
    Brand selectByBrandName(String brandName);

    /**
     * 根据id动态修改
     */
    void update(Brand brand);

    /**
     * 动态新增
     */
    void insert(Brand brand);

    /**
     * 根据ids删除
     */
    void deleteByIds(List<Integer> ids);
}
