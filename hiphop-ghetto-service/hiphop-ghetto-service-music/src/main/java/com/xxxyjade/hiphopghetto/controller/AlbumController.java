package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.MusicSortType;
import com.xxxyjade.hiphopghetto.pojo.dto.MusicPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.vo.AlbumDetailVO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.AlbumService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/album")
@Tag(name = "AlbumController", description = "专辑相关接口")
@AllArgsConstructor
@Slf4j
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/page")
    public Result<PageVO<Album>> page(
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value = "offset", defaultValue = "1") Integer offset,
            @RequestParam(value = "sort", required = false) MusicSortType sort) {
        MusicPageQueryDTO pageQueryDTO = MusicPageQueryDTO.builder()
                .limit(limit)
                .offset(offset)
                .sort(sort)
                .build();
        log.info("分页查询专辑: {}", pageQueryDTO);
        return Result.success(albumService.page(pageQueryDTO));
    }

    @GetMapping("/{id}")
    public Result<AlbumDetailVO> detail(@PathVariable("id") String id) {
        log.info("查询专辑详情: {}",id);
        return Result.success(albumService.detail(id));
    }

}
