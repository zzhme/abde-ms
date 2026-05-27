package com.zzh.service;

import com.zzh.pojo.Dept;

import java.util.List;

/**
 * 部门管理业务层接口
 */
public interface DeptService {
    /**
     * 查询所有
     * @return
     */
    List<Dept> selectAll();

    /**
     * 新增
     * @param dept
     */
    void insert(Dept dept);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Dept selectById(Integer id);

    /**
     * 根据id修改
     * @param dept
     */
    void update(Dept dept);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);
}
