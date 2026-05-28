package com.zzh.dept.mapper;

import com.zzh.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 部门管理持久层接口
 */
public interface DeptMapper {
    /**
     * 查询所有
     * @return
     */
    @Select("select * from dept")
    List<Dept> selectAll();

    /**
     * 新增
     * @param dept
     */
    @Insert("insert into dept (id, name, create_time, update_time) values (#{id},#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from dept where id = #{id}")
    Dept selectById(Integer id);

    /**
     * 根据id修改
     * @param dept
     */
    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);

    /**
     * 根据id删除
     * @param id
     */
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from dept where name=#{name};")
    Dept selectByName(String name);
}
