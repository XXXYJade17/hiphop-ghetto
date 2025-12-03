package com.xxxyjade.hiphopghetto.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "PageVO", description = "分页查询VO")
public class PageVO<T> implements Serializable {

    @Schema(name = "total", description = "总记录数")
    private Long total;

    @Schema(name = "data", description = "数据")
    private List<T> data;

}
