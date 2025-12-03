package com.xxxyjade.hiphopghetto.pojo.dto;

import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "StatsOperationDTO", description = "统计操作DTO")
public class StatsOperationDTO {

    @Schema(name = "id", description = "资源id")
    private Long id;

    @Schema(name = "resourceType", description = "资源类型")
    private ResourceType resourceType;

    @Schema(name = "statsType", description = "统计类型")
    private StatsType statsType;

    @Schema(name = "operationType", description = "操作类型")
    private OperationType operationType;

}
