package com.zzh.login.web.controller;

import com.zzh.login.service.LoginService;
import com.zzh.pojo.Emp;
import com.zzh.pojo.Result;
import com.zzh.utils.JwtTokeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        Emp loginEmp = loginService.login(emp);
        if (loginEmp == null) {
            return Result.error("用户名或密码错误！");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", loginEmp.getId());
        map.put("username", loginEmp.getUsername());
        map.put("name", loginEmp.getName());

        String token = JwtTokeyUtils.generateJwtToken(map);

        return Result.success((Object) token);
    }
}
