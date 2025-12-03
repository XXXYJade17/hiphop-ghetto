package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.enums.SortType;
import com.xxxyjade.hiphopghetto.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.IAlbumService;
import com.xxxyjade.hiphopghetto.pojo.vo.AlbumDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
@Tag(name = "AlbumController", description = "专辑相关接口")
@AllArgsConstructor
@Slf4j
public class AlbumController {

    private final IAlbumService albumService;

    @GetMapping
    @Operation(summary = "（条件）分页查询专辑")
    public Result<PageVO<Album>> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) SortType sortType) {
        PageQueryDTO pageQueryDTO = PageQueryDTO.builder()
                .page(page)
                .size(size)
                .sortType(sortType)
                .build();
        log.info("分页查询专辑:{}",pageQueryDTO);
        return Result.success(albumService.page(pageQueryDTO));
    }

    @Operation(summary = "查询专辑详情")
    @GetMapping("/{id}")
    public Result<AlbumDetailVO> detail(@PathVariable("id") Long id) {
        log.info("查询专辑详情:{}",id);
        return Result.success(albumService.detail(id));
    }

}
