    package com.zzh.service.impl;

    import com.github.pagehelper.PageHelper;
    import com.github.pagehelper.PageInfo;
    import com.zzh.mappers.ProductMapper;
    import com.zzh.pojo.PageBean;
    import com.zzh.pojo.Product;
    import com.zzh.pojo.ProductPageQuery;
    import com.zzh.service.ProductService;
    import com.zzh.web.exception.ProductException;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;
    import java.util.List;

    @Service
    public class ProductServiceImpl implements ProductService {
        @Autowired
        ProductMapper productMapper;


        /**
         * 多条件分页查询
         */

        @Override
        public PageBean<Product> selectAll(ProductPageQuery productPageQuery) {
            if (productPageQuery == null) {
                throw new ProductException("查询条件不能为空");
            }

            String name = productPageQuery.getName();
            if (name != null && !name.trim().isEmpty()) {
                productPageQuery.setName('%'+name+'%');
            }

            Integer pageNum = productPageQuery.getCurrentPage();
            Integer pageSize = productPageQuery.getPageSize();


            PageHelper.startPage(pageNum, pageSize);

            List<Product> products = productMapper.selectAll(productPageQuery);

            PageInfo<Product> productPageInfo = new PageInfo<>(products);

            return new PageBean<>(productPageInfo.getTotal(),productPageInfo.getList());
        }

        /**
         * 根据id查询
         */
        @Override
        public Product selectById(Integer id) {
            return productMapper.selectById(id);
        }

        /**
         * 根据name查询
         */
        @Override
        public Product selectByName(String name) {
            return productMapper.selectByName(name);
        }

        /**
         * 根据id修改
         */
        @Override
        public void update(Product product) {
            productMapper.update(product);
        }

        /**
         * 新增
         */
        @Override
        public void insert(Product product) {
            if (productMapper.selectByName(product.getName()) != null) {
                throw new ProductException("产品名称已存在!");
            }

            LocalDateTime localDateTime = LocalDateTime.now();

            product.setCreateTime(localDateTime);
            product.setUpdateTime(localDateTime);

            productMapper.insert(product);
        }

        /**
         * 根据id删除
         */
        @Override
        public void deleteById(Integer id) {
            productMapper.deleteById(id);
        }

        /**
         * 批量删除
         */
        @Override
        public void deleteByIds(List<Integer> ids) {
            productMapper.deleteByIds(ids);
        }
    }
