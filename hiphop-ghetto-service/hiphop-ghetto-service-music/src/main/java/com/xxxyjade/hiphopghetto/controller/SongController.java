package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.MusicSortType;
import com.xxxyjade.hiphopghetto.pojo.dto.MusicPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.SongService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/song")
@Tag(name = "SongController", description = "歌曲相关接口")
@AllArgsConstructor
@Slf4j
public class SongController {

    private final SongService songService;

    @GetMapping("/page")
    public Result<PageVO<Song>> page(@RequestParam(value = "limit", defaultValue = "20") Integer limit,
                                      @RequestParam(value = "offset", defaultValue = "1") Integer offset,
                                      @RequestParam(value = "sort", required = false) MusicSortType sort) {
        MusicPageQueryDTO pageQueryDTO = MusicPageQueryDTO.builder()
                .limit(limit)
                .offset(offset)
                .sort(sort)
                .build();
        log.info("分页查询歌曲: {}", pageQueryDTO);
        return Result.success(songService.page(pageQueryDTO));
    }

    @GetMapping("/{id}")
    public Result<Song> detail(@PathVariable("id") String id) {
        log.info("查询专辑详情: {}",id);
        return Result.success(songService.detail(id));
    }

}
