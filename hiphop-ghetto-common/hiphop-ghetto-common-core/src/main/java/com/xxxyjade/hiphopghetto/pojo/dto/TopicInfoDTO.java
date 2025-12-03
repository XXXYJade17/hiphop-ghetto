package com.xxxyjade.hiphopghetto.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 话题详情DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicInfoDTO {

    // 用户ID
    private Long userId;

    // 话题ID
    private Long id;

}
