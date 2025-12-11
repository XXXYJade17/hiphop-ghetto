package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.pojo.entity.Collection;
import com.xxxyjade.hiphopghetto.pojo.entity.Follow;
import com.xxxyjade.hiphopghetto.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select exists(select 1 from user where username = #{username})")
    Boolean existByUsername(String username);

    @Update("update user " +
            "set fans_count = fans_count + 1 " +
            "where id = #{id}")
    void increaseFansCount(Long id);

    @Update("update user " +
            "set fans_count = fans_count - 1 " +
            "where id = #{id}")
    void decreaseFansCount(Long id);

    @Update("update user " +
            "set follow_count = follow_count + 1 " +
            "where id = #{id}")
    void increaseFollowCount(Long id);

    @Update("update user " +
            "set follow_count = follow_count - 1 " +
            "where id = #{id}")
    void decreaseFollowCount(Long id);

}
