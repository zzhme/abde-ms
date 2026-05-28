package com.zzh.emp.mapper;

import com.zzh.pojo.Emp;
import com.zzh.pojo.EmpPageQuery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//操作员工的Mapper接口
public interface EmpMapper {
    /**
     * 多条件查询分页列表数据
     */
    List<Emp> selectListByCondition(EmpPageQuery empPageQuery);

    /**
     * 新增
     */
    @Insert("insert into emp (username, name, gender, image, job, entrydate, dept_id, create_time, update_time)" +
            "values (#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime});")
    void insert(Emp emp);

    /**
     * 根据id查询
     */
    @Select("select * from emp where id=#{id};")
    Emp selectById(Integer id);

    /**
     * 根据username查询
     */
    @Select("select * from emp where username=#{username}")
    Emp selectByUsername(String username);

    /**
     * 根据id修改
     */
    void update(Emp emp);

    /**
     * 根据id批量（单个或多个）
     */
    void deleteByIds(@Param("ids") List<Integer> ids);

    /**
     * 查询用户名和密码
     */
    @Select("select * from emp where username=#{username} and password=#{password};")
    Emp selectByUsernameAndPassword(Emp emp);
}