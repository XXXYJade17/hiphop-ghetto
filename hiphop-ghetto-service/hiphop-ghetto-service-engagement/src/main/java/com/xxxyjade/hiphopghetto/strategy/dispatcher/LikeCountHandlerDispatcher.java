package com.xxxyjade.hiphopghetto.strategy.dispatcher;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameworkException;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.strategy.LikeCountStrategy;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class LikeCountHandlerDispatcher {


    private final Map<ResourceType, LikeCountStrategy> handlerMap;

    public LikeCountHandlerDispatcher(List<LikeCountStrategy> strategies) {
        this.handlerMap = new EnumMap<>(ResourceType.class);
        for (LikeCountStrategy strategy : strategies) {
            List<ResourceType> supports = strategy.supports();
            supports.forEach(support -> {
                if (handlerMap.containsKey(support)) {
                    throw new HipHopGhettoFrameworkException("类型[" + support + "]存在重复的处理器");
                }
                handlerMap.put(support, strategy);
            });

        }
    }

    public void dispatch(StatsUpdateDTO statsUpdateDTO) {
        // 提取消息类型标识
        ResourceType support = statsUpdateDTO.getResourceType();
        if (support == null) {
            throw new HipHopGhettoFrameworkException("缺少类型标识");
        }

        // 查找对应的策略
        LikeCountStrategy handler = handlerMap.get(support);
        if (handler == null) {
            throw new HipHopGhettoFrameworkException("不支持的类型：" + support);
        }

        handler.handle(statsUpdateDTO);
    }

}
