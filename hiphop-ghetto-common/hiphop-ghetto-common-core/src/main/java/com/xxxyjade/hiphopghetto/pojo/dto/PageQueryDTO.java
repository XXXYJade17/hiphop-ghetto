package com.xxxyjade.hiphopghetto.pojo.dto;

import com.xxxyjade.hiphopghetto.enums.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "PageQueryDTO", description = "分页查询DTO")
public class PageQueryDTO {

    @Schema(name = "userId", description = "用户id")
    private Long userId;

    @Schema(name = "parentId", description = "评论上级id")
    private Long parentId;

    @NotNull(message = "页数不能为空")
    @Schema(name = "page", description = "页数")
    private Integer page;

    @NotNull(message = "记录数不能为空")
    @Schema(name = "size", description = "记录数")
    private Integer size;

    @Schema(name = "sortType", description = "排序方式")
    private SortType sortType = SortType.DEFAULT;

}
