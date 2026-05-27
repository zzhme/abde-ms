package com.zzh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.mappers.BrandMapper;
import com.zzh.pojo.Brand;
import com.zzh.pojo.BrandPageQuery;
import com.zzh.pojo.PageBean;
import com.zzh.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandMapper brandMapper;

    /**
     * 多条件分页查询
     *
     * @param brandPageQuery
     */
    @Override
    public PageBean<Brand> selectByCondition(BrandPageQuery brandPageQuery, Integer pageNum, Integer pageSize) {
        //修改brandName和companyName为模糊查询格式
        String brandName = brandPageQuery.getBrandName();
        String companyName = brandPageQuery.getCompanyName();
        if (brandName != null && !brandName.trim().isEmpty()) {
            brandPageQuery.setBrandName('%'+brandName+'%');
        }
        if (companyName != null && !companyName.trim().isEmpty()) {
            brandPageQuery.setCompanyName('%'+companyName+'%');
        }

        PageHelper.startPage(pageNum, pageSize);

        //查询数据
        List<Brand> brands = brandMapper.selectByCondition(brandPageQuery);
        //创建PageInfo对象
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        return new PageBean<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 根据id查询
     *
     * @param id
     */
    @Override
    public Brand selectById(Integer id) {
        return brandMapper.selectById(id);
    }

    /**
     * 根据brandName查询
     *
     * @param brandName
     */
    @Override
    public Brand selectByBrandName(String brandName) {
        return brandMapper.selectByBrandName(brandName);
    }

    /**
     * 根据id动态修改
     *
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    /**
     * 动态新增
     *
     * @param brand
     */
    @Override
    public void insert(Brand brand) {
        brandMapper.insert(brand);
    }

    /**
     * 根据ids删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<Integer> ids) {
        brandMapper.deleteByIds(ids);
    }
}
