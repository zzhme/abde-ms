package com.zzh.feign.client;

import com.zzh.pojo.Emp;
import com.zzh.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("emp-service")
public interface EmpClient {
    @PostMapping("/emps/auth")
    Result<Emp> selectByUsernameAndPassword(@RequestBody Emp emp);
}
