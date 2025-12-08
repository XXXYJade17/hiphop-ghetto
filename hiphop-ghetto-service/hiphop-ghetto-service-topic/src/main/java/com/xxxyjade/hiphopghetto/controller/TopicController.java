package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.TopicSortType;
import com.xxxyjade.hiphopghetto.pojo.dto.*;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.pojo.vo.TopicInfoVO;
import com.xxxyjade.hiphopghetto.pojo.vo.TopicVO;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.ITopicService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
@Tag(name = "TopicController", description = "话题相关接口")
@AllArgsConstructor
@Slf4j
public class TopicController {

    private final ITopicService topicService;
    @GetMapping
    @Operation(summary = "分页查询话题")
    public Result<PageVO<TopicVO>> page(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sortType",defaultValue = "DEFAULT") TopicSortType sortType) {
        TopicPageQueryDTO pageQueryDTO = TopicPageQueryDTO.builder()
                .userId(ThreadUtil.getUserId())
                .page(page)
                .size(size)
                .sortType(sortType)
                .build();
        return Result.success(topicService.page(pageQueryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询话题详情")
    public Result<TopicInfoVO> info(@PathVariable Long id) {
        TopicInfoDTO topicInfoDTO = TopicInfoDTO.builder()
                .id(id)
                .userId(ThreadUtil.getUserId())
                .build();
        return Result.success(topicService.info(topicInfoDTO));
    }

    @PostMapping
    @Operation(summary = "创建话题")
    public Result<Void> create(@Valid @RequestBody TopicDTO topicDTO) {
        TopicCreateDTO topicCreateDTO = TopicCreateDTO.builder()
                .userId(ThreadUtil.getUserId())
                .title(topicDTO.getTitle())
                .content(topicDTO.getContent())
                .coverUrl(topicDTO.getCoverUrl())
                .build();
        topicService.create(topicCreateDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除话题")
    public Result<Void> delete(@PathVariable Long id) {
        topicService.delete(id);
        return Result.success();
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新话题")
    public Result<Void> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody TopicDTO topicDTO) {
        TopicUpdateDTO topicUpdateDTO = TopicUpdateDTO.builder()
                .id(id)
                .content(topicDTO.getContent())
                .coverUrl(topicDTO.getCoverUrl())
                .build();
        topicService.update(topicUpdateDTO);
        return Result.success();
    }

}
