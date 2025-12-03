package com.xxxyjade.hiphopghetto.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 话题创建DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicCreateDTO {

    // 用户ID
    private Long userId;

    // 标题
    private String title;

    // 内容
    private String content;

    // 封面
    private String coverUrl;

}
