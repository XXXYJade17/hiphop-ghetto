package com.xxxyjade.hiphopghetto.pojo.dto;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "LikedDTO", description = "（查询/创建/取消）点赞DTO")
public class LikeDTO {

    @Schema(name = "userId", description = "用户id")
    private Long userId;

    @Schema(name = "resourceId", description = "资源id")
    private Long resourceId;

    @Schema(name = "resourceType", description = "资源类型")
    private ResourceType resourceType;

}
