package com.zzh.login.service.impl;

import com.zzh.exception.EmpException;
import com.zzh.feign.client.EmpClient;
import com.zzh.login.service.LoginService;
import com.zzh.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    EmpClient empClient;

    @Override
    public Emp login(Emp emp) {
        if (emp.getUsername() == null || emp.getPassword() == null) {
            throw new EmpException("用户名和密码不能为空");
        }

        return empClient.selectByUsernameAndPassword(emp).getData();
    }
}
