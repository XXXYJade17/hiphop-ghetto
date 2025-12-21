package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.pojo.entity.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TopicMapper extends BaseMapper<Topic> {

    @Update("update topic " +
            "set #{stats} = #{stats} + #{value} " +
            "where id = #{id}")
    int updateStats(Long id, StatsType stats, Integer value);

}
