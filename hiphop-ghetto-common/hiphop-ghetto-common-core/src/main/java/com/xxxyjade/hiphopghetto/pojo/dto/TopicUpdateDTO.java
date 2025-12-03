package com.xxxyjade.hiphopghetto.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 话题更新DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicUpdateDTO {

    // 话题ID
    private Long id;

    // 内容
    private String content;

    // 封面
    private String coverUrl;

}
