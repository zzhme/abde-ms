package com.zzh.service.impl;

import com.zzh.mappers.EmpMapper;
import com.zzh.pojo.Emp;
import com.zzh.service.LoginService;
import com.zzh.web.exception.EmpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    EmpMapper empMapper;

    @Override
    public Emp login(Emp emp) {
        if (emp.getUsername() == null || emp.getPassword() == null) {
            throw new EmpException("用户名和密码不能为空");
        }

        return empMapper.selectByUsernameAndPassword(emp);
    }
}
