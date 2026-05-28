package com.zzh.brand.web.controller;

import com.zzh.brand.service.BrandService;
import com.zzh.pojo.Brand;
import com.zzh.pojo.BrandPageQuery;
import com.zzh.pojo.PageBean;
import com.zzh.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 多条件分页查询
     */
    @PostMapping("/{pageNum}/{pageSize}")
    public Result<PageBean<Brand>> selectByCondition(@RequestBody BrandPageQuery brandPageQuery,
                                    @PathVariable Integer pageNum,
                                    @PathVariable Integer pageSize) {
        PageBean<Brand> brandPageBean = brandService.selectByCondition(brandPageQuery, pageNum, pageSize);
        return Result.success(brandPageBean);
    }

    /**
     * 根据id查询
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {
        return Result.success(brandService.selectById(id));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        brandService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 根据id删除
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        brandService.deleteByIds(List.of(id));
        return Result.success();
    }

    /**
     * 新增
     */
    @PostMapping
    public Result insert(@RequestBody Brand brand) {
        brandService.insert(brand);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping
    public Result update(@RequestBody Brand brand) {
        brandService.update(brand);
        return Result.success();
    }


}
