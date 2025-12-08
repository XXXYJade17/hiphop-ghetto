package com.xxxyjade.hiphopghetto.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicStats {

    @Builder.Default
    private Double avgScore = null;

    @Builder.Default
    private Integer ratingCount = 0;

    @Builder.Default
    private Integer collectionCount = 0;

    @Builder.Default
    private Integer commentCount = 0;

}
