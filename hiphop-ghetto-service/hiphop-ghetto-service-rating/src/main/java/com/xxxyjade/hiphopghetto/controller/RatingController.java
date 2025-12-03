package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.dto.CollectionDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.IsCollectedDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.RatingDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.SelectScoreDTO;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.IRatingService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
@Tag(name = "RatingController", description = "评分相关接口")
@AllArgsConstructor
@Slf4j
public class RatingController {

    private final IRatingService ratingService;

    @GetMapping("/{id}")
    @Operation(summary = "查询评分")
    public Result<Integer> selectScore (
            @PathVariable("id") Long id,
            @RequestParam("resourceType") ResourceType resourceType) {
        SelectScoreDTO selectScoreDTO = SelectScoreDTO.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(resourceType)
                .build();
        return Result.success(ratingService.selectScore(selectScoreDTO));
    }

    @PostMapping("/{id}")
    @Operation(summary = "创建/修改评分")
    public Result<Void> score(
            @PathVariable("id") Long id,
            @RequestParam("resourceType") ResourceType resourceType,
            @RequestParam("score") Integer score) {
        RatingDTO ratingDTO = RatingDTO.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(resourceType)
                .score(score)
                .build();
        ratingService.rate(ratingDTO);
        return Result.success();
    }

}
