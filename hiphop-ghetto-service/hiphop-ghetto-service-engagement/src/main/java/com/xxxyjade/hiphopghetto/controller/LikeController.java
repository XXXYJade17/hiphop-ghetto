package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.entity.Like;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.LikeService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@Tag(name = "LikeController", description = "点赞相关接口")
@AllArgsConstructor
@Slf4j
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/{type}/{id}")
    @Operation(summary = "是否点赞")
    public Result<Boolean> isLiked(
            @PathVariable("type") ResourceType type,
            @PathVariable("id") Long id) {
        Like like = Like.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(type)
                .build();
        return Result.success(likeService.select(like));
    }

    @PostMapping("/{type}/{id}")
    @Operation(summary = "点赞")
    public Result<Void> like(
            @PathVariable("type") ResourceType type,
            @PathVariable("id") Long id) {
        Like like = Like.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(type)
                .isLiked(true)
                .build();
        likeService.like(like);
        return Result.success();
    }

    @DeleteMapping("/{type}/{id}")
    @Operation(summary = "取消点赞")
    public Result<Void> unlike(
            @PathVariable("type") ResourceType type,
            @PathVariable("id") Long id) {
        Like like = Like.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(type)
                .build();
        likeService.unlike(like);
        return Result.success();
    }

}
