package com.zzh.service.impl;

import com.zzh.mappers.AccountMapper;
import com.zzh.pojo.Account;
import com.zzh.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 查询所有
     */
    @Override
    public List<Account> selectAll() {
        return accountMapper.selectAll();
    }

    /**
     * 新增
     *
     * @param account
     */
    @Override
    public void insert(Account account) {
        accountMapper.insert(account);
    }

    /**
     * 根据id查询
     *
     * @param id
     */
    @Override
    public Account selectById(Integer id) {
        return accountMapper.selectById(id);
    }


    /**
     * 根据名字查询
     *
     * @param name
     * @return
     */
    @Override
    public Account selectByName(String name) {
        return accountMapper.selectByName(name);
    }

    /**
     * 根据id修改
     */
    @Override
    public void update(Account account) {
        accountMapper.update(account);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        accountMapper.deleteById(id);
    }

    /**
     * 转账业务
     *
     * @param outName
     * @param inName
     * @param money
     */
    @Override
    @Transactional
    public void transfer(String outName, String inName, double money) {
        Account outAccount = accountMapper.selectByName(outName);
        Account inAccount = accountMapper.selectByName(inName);
        if (outAccount.getMoney() >= money) {
            outAccount.setMoney(outAccount.getMoney() - money);
            inAccount.setMoney(inAccount.getMoney() + money);
            accountMapper.update(outAccount);
            accountMapper.update(inAccount);
        }
    }
}