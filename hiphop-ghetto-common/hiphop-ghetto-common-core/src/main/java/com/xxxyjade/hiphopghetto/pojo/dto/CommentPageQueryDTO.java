package com.xxxyjade.hiphopghetto.pojo.dto;

import com.xxxyjade.hiphopghetto.enums.CommentSortType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageQueryDTO {

    private Long userId;
    private Long parentId;
    private Integer offset;
    private Integer limit;
    private CommentSortType sort;

}
