package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.dto.LikeDTO;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.ILikeService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@Tag(name = "LikeController", description = "点赞相关接口")
@AllArgsConstructor
@Slf4j
public class LikeController {

    private final ILikeService likeService;

    @GetMapping("/{id}")
    @Operation(summary = "查询是否点赞")
    public Result<Boolean> isLiked(
            @PathVariable("id") Long id,
            @RequestParam("resourceType") ResourceType resourceType) {
        LikeDTO likeDTO = LikeDTO.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(resourceType)
                .build();
        return Result.success(likeService.isLiked(likeDTO));
    }

    @PostMapping("/{id}")
    @Operation(summary = "创建点赞")
    public Result<Void> like(
            @PathVariable("id") Long id,
            @RequestParam("resourceType") ResourceType resourceType) {
        LikeDTO likeDTO = LikeDTO.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(resourceType)
                .build();
        likeService.like(likeDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "取消点赞")
    public Result<Void> cancel(
            @PathVariable("id") Long id,
            @RequestParam("resourceType") ResourceType resourceType) {
        LikeDTO likeDTO = LikeDTO.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(resourceType)
                .build();
        likeService.cancel(likeDTO);
        return Result.success();
    }

}
