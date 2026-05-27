package com.zzh.mappers;

import com.zzh.pojo.Product;
import com.zzh.pojo.ProductPageQuery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductMapper {
    /**
     * 多条件动态查询
     * @return
     */
    List<Product> selectAll(ProductPageQuery productPageQuery);

    /**0898097-6
     * 根据id查询
     */
    @Select("select * from product where id=#{id};")
    Product selectById(Integer id);

    /**
     * 根据名字查询
     */
    @Select("select * from product where name=#{name};")
    Product selectByName(String name);


    /**
     * 根据id修改
     */
    void update(Product product);

    /**
     * 新增
     */
    void insert(Product product);

    /**
     * 根据id删除
     */
    @Delete("delete from product where id=#{id};")
    void deleteById(Integer id);

    /**
     * 批量删除
     */
    void deleteByIds(@Param("ids") List<Integer> ids);
}
