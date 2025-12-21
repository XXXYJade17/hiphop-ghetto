package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.TopicSortType;
import com.xxxyjade.hiphopghetto.pojo.dto.*;
import com.xxxyjade.hiphopghetto.pojo.entity.Topic;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.TopicService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topic")
@Tag(name = "TopicController", description = "话题相关接口")
@AllArgsConstructor
@Slf4j
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/page")
    @Operation(summary = "分页查询话题")
    public Result<PageVO<Topic>> page(
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value = "offset", defaultValue = "1") Integer offset,
            @RequestParam(value = "sort", required = false) TopicSortType sort) {
        TopicPageQueryDTO pageQueryDTO = TopicPageQueryDTO.builder()
                .offset(offset)
                .limit(limit)
                .sort(sort)
                .build();
        return Result.success(topicService.page(pageQueryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询话题详情")
    public Result<Topic> detail(@PathVariable Long id) {
        Topic topic = Topic.builder()
                .id(id)
                .userId(ThreadUtil.getUserId())
                .build();
        return Result.success(topicService.detail(topic));
    }

    @PostMapping
    @Operation(summary = "创建话题")
    public Result<Void> create(@Valid @RequestBody TopicDTO topicDTO) {
        Topic topic = Topic.builder()
                .userId(ThreadUtil.getUserId())
                .nickname(ThreadUtil.getNickname())
                .title(topicDTO.getTitle())
                .content(topicDTO.getContent())
                .coverUrl(topicDTO.getCoverUrl())
                .build();
        topicService.create(topic);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除话题")
    public Result<Void> delete(@PathVariable Long id) {
        topicService.delete(id);
        return Result.success();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "更新话题")
    public Result<Void> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody TopicDTO topicDTO) {
        Topic topic = Topic.builder()
                .id(id)
                .userId(ThreadUtil.getUserId())
                .nickname(ThreadUtil.getNickname())
                .title(topicDTO.getTitle())
                .content(topicDTO.getContent())
                .coverUrl(topicDTO.getCoverUrl())
                .build();
        topicService.update(topic);
        return Result.success();
    }

}
