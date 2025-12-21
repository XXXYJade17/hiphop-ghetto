package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;

import java.util.List;

public interface LikeCountStrategy {

    // 处理方法
    void handle(StatsUpdateDTO statsUpdateDTO);

    // 处理器支持类型
    List<ResourceType> supports();

}
