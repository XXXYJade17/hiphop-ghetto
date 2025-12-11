package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.entity.Collection;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.CollectionService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collection")
@Tag(name = "CollectionController", description = "收藏相关接口")
@AllArgsConstructor
@Slf4j
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping("/{type}/{id}")
    @Operation(summary = "是否收藏")
    public Result<Boolean> select (
            @PathVariable("type") ResourceType type,
            @PathVariable("id") Long id) {
        Collection collection = Collection.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(type)
                .build();
        return Result.success(collectionService.select(collection));
    }

    @PostMapping("/{type}/{id}")
    @Operation(summary = "收藏")
    public Result<Void> collect(
            @PathVariable("type") ResourceType type,
            @PathVariable("id") Long id) {
        Collection collection = Collection.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(type)
                .isCollected(true)
                .build();
        collectionService.collect(collection);
        return Result.success();
    }

    @DeleteMapping("/{type}/{id}")
    @Operation(summary = "取消收藏")
    public Result<Void> uncollect(
            @PathVariable("type") ResourceType type,
            @PathVariable("id") Long id) {
        Collection collection = Collection.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(type)
                .isCollected(false)
                .build();
        collectionService.uncollect(collection);
        return Result.success();
    }

}
