package com.zzh.service;

import com.zzh.pojo.Account;

import java.util.List;

public interface AccountService {
    /**
     * 查询所有
     */
    List<Account> selectAll();

    /**
     * 新增
     */
    void insert(Account account);

    /**
     * 根据id查询
     */
    Account selectById(Integer id);


    /**
     * 根据名字查询
     */
    Account selectByName(String name);

    /**
     * 根据id修改
     */
    void update(Account account);

    /**
     * 根据id删除
     */
    void deleteById(Integer id);

    /**
     * 转账业务
     */
    void transfer(String outName, String inName, double money);
}