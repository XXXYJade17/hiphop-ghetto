package com.xxxyjade.hiphopghetto.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowDTO {

    private Long userId;

    private Long followId;

    private Boolean isFollow;

}
