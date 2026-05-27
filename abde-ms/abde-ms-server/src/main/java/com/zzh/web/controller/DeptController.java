package com.zzh.web.controller;

import com.zzh.pojo.Dept;
import com.zzh.pojo.Result;
import com.zzh.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器类
 */
@RestController
@RequestMapping("/depts")
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 查询所有
     */
    @GetMapping
    public Result selectAll() {
        //调用业务层接口方法
        List<Dept> deptList = deptService.selectAll();
        //返回结果数据
        return Result.success(deptList);
    }

    /**
     * 新增
     * @param dept
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody Dept dept) {
        //调用业务层接口方法
        deptService.insert(dept);
        return Result.success("恭喜您,新增部门成功!!!!");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable("id") Integer id) {
        //调用业务层接口方法
        Dept dept = deptService.selectById(id);
        return Result.success(dept);
    }

    /**
     * 根据id修改
     * @param dept
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        //调用业务层接口方法
        deptService.update(dept);
        return Result.success("恭喜您修改部门成功!!!!");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        //调用业务层接口方法
        deptService.deleteById(id);
        return Result.success("恭喜您,删除部门成功!!!!");
    }
}
