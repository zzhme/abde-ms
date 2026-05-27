package com.zzh.web.controller;

import com.zzh.pojo.PageBean;
import com.zzh.pojo.Product;
import com.zzh.pojo.ProductPageQuery;
import com.zzh.pojo.Result;
import com.zzh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    /**
     * 多条件分页查询
     *
     * @param productPageQuery
     * @return
     */

    @PostMapping
    public Result selectAll(@RequestBody ProductPageQuery productPageQuery) {
        PageBean pageBean = productService.selectAll(productPageQuery);
        return Result.success(pageBean);
    }


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {
        return Result.success(productService.selectById(id));
    }

    /**
     * 根据id修改
     */
    @PutMapping
    public Result update(@RequestBody Product product) {
        productService.update(product);
        return Result.success();
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    public Result insert(@RequestBody Product product) {
        productService.insert(product);
        return Result.success();
    }

    /**
     * 根据id删除
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        productService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch-delete")
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        productService.deleteByIds(ids);
        return Result.success();
    }
}
