package com.zzh.emp.service;

import com.zzh.pojo.Emp;
import com.zzh.pojo.EmpPageQuery;
import com.zzh.pojo.PageBean;

import java.util.List;

//操作Emp的业务层接口
public interface EmpService {
    /**
     * 多条件分页查询
     * @param empPageQuery
     * @return
     */
    PageBean<Emp> selectByCondition(EmpPageQuery empPageQuery);

    /**
     *  新增
     * @param emp
     */
    void insert(Emp emp);

    /**
     * 根据id查询
     */
    Emp selectById(Integer id);

    /**
     * 根据id修改
     * @param emp
     */
    void update(Emp emp);

    /**
     * 根据id列表删除
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据用户名和密码查询用户
     * @param emp
     * @return
     */
    Emp selectByUsernameAndPassword(Emp emp);
}