package com.zzh.web.controller;

import com.zzh.pojo.Account;
import com.zzh.pojo.Result;
import com.zzh.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * 查询所有
     */
    @GetMapping("selectAll")
    public Result selectAll() {
        //调用业务层接口方法
        List<Account> accountList = accountService.selectAll();
        //返回结果数据
        return Result.success(accountList);
    }

    /**
     * 新增
     *
     * @param account
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody Account account) {
        //调用业务层接口方法
        accountService.insert(account);
        return Result.success("恭喜您,新增账户成功!!!!");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable("id") Integer id) {
        //调用业务层接口方法
        Account account = accountService.selectById(id);
        return Result.success(account);
    }

    @GetMapping("/selectByName")
    public Result selectByName(String name) {
        //调用业务层接口方法
        Account account = accountService.selectByName(name);
        return Result.success(account);
    }


    /**
     * 根据id修改
     *
     * @param account
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Account account) {
        //调用业务层接口方法
        accountService.update(account);
        return Result.success("恭喜您修改账户成功!!!!");
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        //调用业务层接口方法
        accountService.deleteById(id);
        return Result.success("恭喜您,删除账户成功!!!!");
    }

    @GetMapping("/transfer")
    public Result transfer(String outName, String inName, double money) {
        accountService.transfer(outName, inName, money);
        return Result.success("恭喜您,转账成功!!!!");
    }
}
