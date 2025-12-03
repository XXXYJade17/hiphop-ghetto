package com.xxxyjade.hiphopghetto.pojo.dto;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询评分DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Schema(title = "SelectScoreDTO", description = "查询评分DTO")
public class SelectScoreDTO {

//    @Schema(name = "userId", description = "用户id")
    private Long userId;

//    @Schema(name = "resourceId", description = "资源id")
    private Long resourceId;

//    @Schema(name = "resourceType", description = "资源类型")
    private ResourceType resourceType;

}
