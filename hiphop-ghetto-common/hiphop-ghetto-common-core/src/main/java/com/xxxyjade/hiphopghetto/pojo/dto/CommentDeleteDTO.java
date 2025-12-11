package com.xxxyjade.hiphopghetto.pojo.dto;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDeleteDTO {

    @NotNull
    private Long parentId;
    @NotNull
    private ResourceType parentType;
    @NotNull
    private Long rootId;
    @NotNull
    private ResourceType rootType;

}
