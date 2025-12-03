package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.enums.SortType;
import com.xxxyjade.hiphopghetto.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.ISongService;
import com.xxxyjade.hiphopghetto.pojo.vo.SongDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
@Tag(name = "SongController", description = "歌曲相关接口")
@AllArgsConstructor
@Slf4j
public class SongController {

    private final ISongService songService;

    @GetMapping
    @Operation(summary = "（条件）分页查询歌曲")
    public Result<PageVO<Song>> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) SortType sortType) {
        PageQueryDTO pageQueryDTO = PageQueryDTO.builder()
                .page(page)
                .size(size)
                .sortType(sortType)
                .build();
        log.info("分页查询歌曲:{}", pageQueryDTO);
        return Result.success(songService.page(pageQueryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询歌曲详情")
    public Result<SongDetailVO> detail(@PathVariable("id") Long id) {
        log.info("查询歌曲详情:{}",id);
        return Result.success(songService.detail(id));
    }

}
