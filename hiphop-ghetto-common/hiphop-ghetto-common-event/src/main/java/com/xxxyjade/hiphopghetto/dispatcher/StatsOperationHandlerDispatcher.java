package com.xxxyjade.hiphopghetto.dispatcher;

import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameworkException;
import com.xxxyjade.hiphopghetto.strategy.IStrategy;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class StatsOperationHandlerDispatcher {

    private final Map<ResourceType, Map<StatsType, Map<OperationType, IStrategy<StatsOperationEvent>>>>  strategyMap;

    public StatsOperationHandlerDispatcher(List<IStrategy<StatsOperationEvent>> strategies) {
        this.strategyMap = new EnumMap<>(ResourceType.class);
        for (IStrategy<StatsOperationEvent> strategy : strategies) {
            Map<StatsType, Map<OperationType, IStrategy<StatsOperationEvent>>> statsTypeMap = strategyMap.computeIfAbsent(
                    strategy.getResourceType(),
                    k -> new EnumMap<>(StatsType.class)
            );
            Map<OperationType, IStrategy<StatsOperationEvent>> operationTypeMap = statsTypeMap.computeIfAbsent(
                    strategy.getStatsType(),
                    k -> new EnumMap<>(OperationType.class)
            );
            if(operationTypeMap.containsKey(strategy.getOperationType())) {
                throw new HipHopGhettoFrameworkException("消息类型[" + strategy.getOperationType() + "]存在重复的处理器");
            }
            operationTypeMap.put(strategy.getOperationType(), strategy);
        }
    }

    public void dispatch(StatsOperationEvent event) {
        // 逐层查找对应的策略处理器
        Map<StatsType, Map<OperationType, IStrategy<StatsOperationEvent>>> statsTypeMap =
                strategyMap.get(event.getResourceType());

        if (statsTypeMap == null) {
            throw new RuntimeException("不支持的资源类型: " + event.getResourceType());
        }

        Map<OperationType, IStrategy<StatsOperationEvent>> operationTypeMap =
                statsTypeMap.get(event.getStatsType());

        if (operationTypeMap == null) {
            throw new RuntimeException("不支持的统计类型: " + event.getStatsType() +
                    " 对于资源类型: " + event.getResourceType());
        }

        IStrategy<StatsOperationEvent> strategy = operationTypeMap.get(event.getOperationType());

        if (strategy == null) {
            throw new RuntimeException("不支持的操作类型: " + event.getOperationType() +
                    " 对于资源类型: " + event.getResourceType() +
                    " 和统计类型: " + event.getStatsType());
        }

        // 执行策略
        strategy.handle(event);
    }
}
