package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.pojo.entity.Like;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper extends BaseMapper<Like> {

//    @Insert("insert into user_like(id, user_id, resource_id, resource_type, is_liked, create_time, update_time) " +
//            "values (#{id}, #{userId}, #{resourceId}, #{resourceType}, #{isLiked}, #{createTime}, #{updateTime}) " +
//            "on duplicate key update " +
//            "is_liked = VALUES(is_liked), " +
//            "update_time = VALUES(update_time)")
//    void upsert(Like like);

}
