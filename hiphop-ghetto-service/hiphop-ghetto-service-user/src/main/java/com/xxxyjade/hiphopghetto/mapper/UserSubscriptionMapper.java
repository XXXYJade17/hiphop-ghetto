package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.pojo.entity.Subscription;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSubscriptionMapper extends BaseMapper<Subscription> {

    @Insert("insert into subscription(id, user_id, subscribed_id, is_subscribed, create_time, update_time) " +
            "values (#{id}, #{userId}, #{subscribedId}, #{isSubscribed}, #{createTime}, #{updateTime}) " +
            "on duplicate key update " +
            "is_subscribed = VALUES(is_subscribed), " +
            "update_time = VALUES(update_time)")
    int upsert(Subscription subscription);

}
