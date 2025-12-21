package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select exists(select 1 from user where username = #{username})")
    Boolean existByUsername(String username);

    @Update("update user " +
            "set #{stats} = #{stats} + #{value} " +
            "where id = #{id}")
    int updateStats(Long id, StatsType stats, Integer value);

}
