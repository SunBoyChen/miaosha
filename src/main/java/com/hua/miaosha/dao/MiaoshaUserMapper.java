package com.hua.miaosha.dao;


import com.hua.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MiaoshaUserMapper {

    @Select("select * from miaosha_user where id = #{id}")
    MiaoshaUser getById(@Param("id") Long id);


    @Update("UPDATE miaosha_user SET password = #{password} WHERE id = #{id}")
    int updatePassword(MiaoshaUser user);

}
