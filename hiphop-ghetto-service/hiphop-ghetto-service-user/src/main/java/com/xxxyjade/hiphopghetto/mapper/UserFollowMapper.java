package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.pojo.entity.Follow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFollowMapper extends BaseMapper<Follow> {

    @Insert("insert into user_follow(id, user_id, followed_id, is_followed, create_time, update_time) " +
            "values (#{id}, #{userId}, #{followedId}, #{isFollowed}, #{createTime}, #{updateTime}) " +
            "on duplicate key update " +
            "is_followed = VALUES(is_followed), " +
            "update_time = VALUES(update_time)")
    int upsert(Follow follow);
}
