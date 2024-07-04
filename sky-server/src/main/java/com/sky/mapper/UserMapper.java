package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 *查询用户
 */

@Mapper

public interface UserMapper {

    @Select("select * from user where openid = #{id}")
    User getByOpenid(String openid);

    //插入数据，需要返回userId，后边生成jwt令牌会用到
    void insert(User user);
}
