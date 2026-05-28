package com.zzh.dept.service.impl;

import com.zzh.dept.mapper.DeptMapper;
import com.zzh.dept.service.DeptService;
import com.zzh.exception.DeptException;
import com.zzh.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门管理业务层接口实现类
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<Dept> selectAll() {
        //调用持久层接口方法
        List<Dept> deptList = deptMapper.selectAll();
        return deptList;
    }

    /**
     * 新增
     *
     * @param dept
     */
    @Override
    //控制事务
    @Transactional
    public void insert(Dept dept) {
        // 数据校验
        if (dept == null) {
            throw new DeptException("新增部门错误！\n数据不能为空！");
        }
        // 获取部门名称
        String name = dept.getName();
        if (name == null || name.trim().isEmpty()) {
            throw new DeptException("新增部门错误！\n部门名称不能为空！");
        }
        Dept existedDept = deptMapper.selectByName(name);
        if (existedDept != null) {
            throw new DeptException("新增部门错误！\n部门名称已存在！");
        }

        LocalDateTime now = LocalDateTime.now();
        //设置创建时间
        dept.setCreateTime(now);
        //设置修改时间
        dept.setUpdateTime(now);
        //调用持久层接口方法
        deptMapper.insert(dept);
        //自杀
        //int i = 1/0;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Dept selectById(Integer id) {
        //调用持久层接口方法
        Dept dept = deptMapper.selectById(id);
        return dept;
    }



    /**
     * 根据id修改
     *
     * @param dept
     */
    @Override
    @Transactional
    public void update(Dept dept) {
        //设置修改时间
        dept.setUpdateTime(LocalDateTime.now());
        //调用持久层接口方法
        deptMapper.update(dept);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }
}
