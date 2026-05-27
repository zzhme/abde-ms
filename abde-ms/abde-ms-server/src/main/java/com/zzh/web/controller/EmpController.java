package com.zzh.web.controller;

import com.zzh.pojo.Emp;
import com.zzh.pojo.EmpPageQuery;
import com.zzh.pojo.PageBean;
import com.zzh.pojo.Result;
import com.zzh.service.EmpService;
import com.zzh.web.exception.EmpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

    /**
     * 多条件分页查询
     * @param empPageQuery
     * @return
     */
    @PostMapping
    public Result selectByCondition(@RequestBody EmpPageQuery empPageQuery) {
        if (empPageQuery == null) {
            throw new EmpException("分页数据不能为空");
        }
        if (empPageQuery.getCurrentPage() == null || empPageQuery.getCurrentPage()<=0) {
            empPageQuery.setCurrentPage(1);
        }
        if (empPageQuery.getPageSize() == null || empPageQuery.getPageSize() <= 0) {
            empPageQuery.setPageSize(5);
        }
        PageBean pageBean = empService.selectByCondition(empPageQuery);
        return Result.success(pageBean);
    }

    /**
     * 新增
     *
     * @param emp
     */
    @PostMapping("/save")
    public Result insert(@RequestBody Emp emp) {
        empService.insert(emp);
        return Result.success("新增员工成功！");
    }

    /**
     * 根据id查询
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {
        Emp emp = empService.selectById(id);
        return Result.success(emp);
    }

    /**
     * 根据id修改
     *
     * @param emp
     */
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        empService.update(emp);
        return Result.success("修改成功");
    }

    /**
     * 根据id列表删除
     */
    @DeleteMapping("/{ids}")
    public Result deleteByIds(@PathVariable List<Integer> ids) {
        empService.deleteByIds(ids);
        return Result.success("删除成功！");
    }
}