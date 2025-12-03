package com.xxxyjade.hiphopghetto.pojo.dto;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建/取消收藏DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Schema(title = "CollectionDTO", description = "创建/取消收藏DTO")
public class CollectionDTO {

    // 用户id
    private Long userId;

    // 资源id
    private Long resourceId;

    // 资源类型
    private ResourceType resourceType;

}
