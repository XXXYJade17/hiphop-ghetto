package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.dto.CollectionDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.IsCollectedDTO;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.ICollectionService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collections")
@Tag(name = "CollectionController", description = "收藏相关接口")
@AllArgsConstructor
@Slf4j
public class CollectionController {

    private final ICollectionService collectionService;

    @GetMapping("/{id}")
    @Operation(summary = "查询是否收藏")
    public Result<Boolean> isCollected(
            @PathVariable("id") Long id,
            @RequestParam("resourceType") ResourceType resourceType) {
        IsCollectedDTO isCollectedDTO = IsCollectedDTO.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(resourceType)
                .build();
        return Result.success(collectionService.select(isCollectedDTO));
    }

    @PostMapping("/{id}")
    @Operation(summary = "创建收藏")
    public Result<Void> collect(
            @PathVariable("id") Long id,
            @RequestParam("resourceType") ResourceType resourceType) {
        CollectionDTO collectionDTO = CollectionDTO.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(resourceType)
                .build();
        collectionService.collect(collectionDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "取消收藏")
    public Result<Void> uncollect(
            @PathVariable("id") Long id,
            @RequestParam("resourceType") ResourceType resourceType) {
        CollectionDTO collectionDTO = CollectionDTO.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(id)
                .resourceType(resourceType)
                .build();
        collectionService.uncollect(collectionDTO);
        return Result.success();
    }

}
