package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.pojo.entity.Rating;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RatingMapper extends BaseMapper<Rating> {

    @Insert("insert into user_rating(id, user_id, resource_id, resource_type, score, create_time, update_time) " +
            "values (#{id}, #{userId}, #{resourceId}, #{resourceType}, #{score}, #{createTime}, #{updateTime}) " +
            "on duplicate key update " +
            "score = VALUES(score), " +
            "update_time = VALUES(update_time)")
    int upsert(Rating rating);

}
