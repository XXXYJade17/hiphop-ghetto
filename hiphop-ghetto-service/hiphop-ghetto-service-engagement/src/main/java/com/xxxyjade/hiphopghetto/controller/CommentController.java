package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.CommentSortType;
import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.dto.CommentCreateDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.CommentDeleteDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.CommentPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.pojo.vo.CommentVO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.CommentService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@Tag(name = "CommentController", description = "评论相关接口")
@AllArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/page/{id}")
    @Operation(summary = "分页查询评论")
    public Result<PageVO<CommentVO>> page(
            @PathVariable("id") Long parentId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", required = false) CommentSortType sortType) {
        CommentPageQueryDTO pageQueryDTO = CommentPageQueryDTO.builder()
                .userId(ThreadUtil.getUserId())
                .parentId(parentId)
                .offset(page)
                .limit(size)
                .sort(sortType)
                .build();
        return Result.success(commentService.page(pageQueryDTO));
    }

    @PostMapping("/{type}/{id}")
    @Operation(summary = "创建评论")
    public Result<Void> create(
            @PathVariable("type") ResourceType type,
            @PathVariable("id") Long id,
            @Valid @RequestBody CommentCreateDTO commentCreateDTO) {
        Comment comment = Comment.builder()
                .userId(ThreadUtil.getUserId())
                .parentId(id)
                .parentType(type)
                .rootId(commentCreateDTO.getRootId())
                .rootType(commentCreateDTO.getRootType())
                .content(commentCreateDTO.getContent())
                .build();
        commentService.create(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论")
    public Result<Void> delete(
            @PathVariable("id") Long id,
            @Valid @RequestBody CommentDeleteDTO commentDeleteDTO) {
        Comment comment = Comment.builder()
                .userId(ThreadUtil.getUserId())
                .id(id)
                .parentId(commentDeleteDTO.getParentId())
                .parentType(commentDeleteDTO.getParentType())
                .rootId(commentDeleteDTO.getRootId())
                .rootType(commentDeleteDTO.getRootType())
                .build();
        commentService.delete(comment);
        return Result.success();
    }

}
