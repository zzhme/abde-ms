package com.zzh.emp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.Log;
import com.zzh.emp.mapper.EmpMapper;
import com.zzh.emp.service.EmpService;
import com.zzh.exception.EmpException;
import com.zzh.pojo.Emp;
import com.zzh.pojo.EmpPageQuery;
import com.zzh.pojo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

//操作Emp的业务层接口实现类
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    /**
     * 分页条件查询
     */
    @Override
    public PageBean<Emp> selectByCondition(EmpPageQuery empPageQuery) {
        //数据校验
        if (empPageQuery == null) {
            throw new EmpException("分页查询条件对象不能为空！");
        }

        String name = empPageQuery.getName();
        if (name != null && !name.trim().equals("")) {
            empPageQuery.setName('%'+name+'%');
        }

        Integer currentPage = empPageQuery.getCurrentPage();
        Integer pageSize = empPageQuery.getPageSize();
        PageHelper.startPage(currentPage,pageSize);

        //计算索引，查询数据
        List<Emp> empList = empMapper.selectListByCondition(empPageQuery);

        PageInfo<Emp> pageInfo = new PageInfo<>(empList);

        return new PageBean<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 新增
     *
     * @param emp
     */
    @Log
    @Override
    @Transactional
    public void insert(Emp emp) {
        Emp existedEmp = empMapper.selectByUsername(emp.getUsername());
        if (existedEmp != null) {
            throw new EmpException("用户名已存在！");
        }
        LocalDateTime now = LocalDateTime.now();
        emp.setCreateTime(now);
        emp.setUpdateTime(now);
        empMapper.insert(emp);
    }

    /**
     * 根据id查询
     *
     * @param id
     */
    @Override
    public Emp selectById(Integer id) {
        return empMapper.selectById(id);
    }

    /**
     * 根据id修改
     *
     * @param emp
     */
    @Log
    @Override
    public void update(Emp emp) {
        LocalDateTime now = LocalDateTime.now();
        emp.setUpdateTime(now);
        empMapper.update(emp);
    }

    /**
     * 根据id列表删除
     *
     * @param ids
     */
    @Log
    @Override
    public void deleteByIds(List<Integer> ids) {
        empMapper.deleteByIds(ids);
    }

    /**
     * 根据用户名和密码查询用户
     *
     * @param emp
     * @return
     */
    @Override
    public Emp selectByUsernameAndPassword(Emp emp) {
        return empMapper.selectByUsernameAndPassword(emp);
    }
}