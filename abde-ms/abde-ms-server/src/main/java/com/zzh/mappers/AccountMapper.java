package com.zzh.mappers;

import com.zzh.pojo.Account;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AccountMapper {
    /**
     * 查询所有
     */
    @Select("select * from account")
    List<Account> selectAll();

    /**
     * 新增
     */
    @Insert("insert into account (name, money, home_address) values(#{name},#{money},#{homeAddress})")
    void insert(Account account);

    /**
     * 根据id查询
     */
    @Select("select * from account where id=#{id}")
    Account selectById(Integer id);

    /**
     * 根据名字查询
     */
    @Select("select * from account where name=#{name}")
    Account selectByName(String name);

    /**
     * 根据id修改
     */
    void update(Account account);

    /**
     * 根据id删除
     */
    @Delete("delete from account where id=#{id}")
    void deleteById(Integer id);
}
