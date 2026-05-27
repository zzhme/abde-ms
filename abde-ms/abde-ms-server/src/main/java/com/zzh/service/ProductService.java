package com.zzh.service;

import com.zzh.pojo.PageBean;
import com.zzh.pojo.Product;
import com.zzh.pojo.ProductPageQuery;

import java.util.List;

public interface ProductService {
    /**
     * 多条件分页查询
     */
    PageBean selectAll(ProductPageQuery productPageQuery);

    Product selectById(Integer id);

    Product selectByName(String name);

    void update(Product product);

    void insert(Product product);

    void deleteById(Integer id);

    void deleteByIds(List<Integer> ids);
}