package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.pojo.entity.Collection;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectionMapper extends BaseMapper<Collection> {

    @Insert("insert into user_collection(id, user_id, resource_id, resource_type, is_collected, create_time, update_time) " +
            "values (#{id}, #{userId}, #{resourceId}, #{resourceType}, #{isCollected}, #{createTime}, #{updateTime}) " +
            "on duplicate key update " +
            "is_collected = VALUES(is_collected), " +
            "update_time = VALUES(update_time)")
    void upsert(Collection collection);

}
