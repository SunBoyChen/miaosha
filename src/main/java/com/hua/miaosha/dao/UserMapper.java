package com.hua.miaosha.dao;

import com.hua.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {


    @Select("select * from user_test where id = #{id}")
    User getById(@Param("id") int id);

    @Select("select * from user_test")
    List<User> getAll();

    @Insert("insert into user_test(id, username,name,age,balance)values(#{id}, #{username},#{name}, #{age},#{balance})")
    public int insert(User user);
}
