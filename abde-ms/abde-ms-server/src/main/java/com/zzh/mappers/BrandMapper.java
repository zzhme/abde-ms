package com.zzh.mappers;

import com.zzh.pojo.Brand;
import com.zzh.pojo.BrandPageQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper {

    /**
     * 多条件分页查询：数据
     */
    List<Brand> selectByCondition(BrandPageQuery brandPageQuery);


    /**
     * 根据id查询
     */
    @Select("select * from brand where id=#{id}")
    Brand selectById(Integer id);


    /**
     * 根据brandName查询
     */
    @Select("select * from brand where brand_name=#{brandName}")
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
    void deleteByIds(@Param("ids") List<Integer> ids);
}
