package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.entity.Rating;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.RatingService;
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

    private final RatingService ratingService;

    @GetMapping("/{type}/{id}")
    @Operation(summary = "查询评分")
    public Result<Integer> select (
            @PathVariable("type") ResourceType type,
            @PathVariable("id") Long id) {
        Rating rating = Rating.builder()
                .id(id)
                .resourceType(type)
                .userId(ThreadUtil.getUserId())
                .build();
        return Result.success(ratingService.select(rating));
    }

    @PostMapping("/{type}/{id}")
    @Operation(summary = "创建/更新评分")
    public Result<Void> save(
            @PathVariable("type") ResourceType type,
            @PathVariable("id") Long id,
            @RequestParam("score") Integer score) {
        Rating rating = Rating.builder()
                .resourceId(id)
                .resourceType(type)
                .score(score)
                .userId(ThreadUtil.getUserId())
                .build();
        ratingService.save(rating);
        return Result.success();
    }

}
