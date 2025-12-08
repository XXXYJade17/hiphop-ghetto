package com.xxxyjade.hiphopghetto.event;


import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsOperationEvent implements IEvent {

    /**
     * 资源id
     */
    private Long resourceId;

    /**
     * 资源类型
     */
    private ResourceType resourceType;

    /**
     * 统计类型
     */
    private StatsType statsType;

    /**
     * 操作类型
     */
    private OperationType operationType;

}
