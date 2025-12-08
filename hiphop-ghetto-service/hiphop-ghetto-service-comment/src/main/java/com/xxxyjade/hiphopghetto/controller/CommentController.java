package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.enums.CommentSortType;
import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.CommentCreateDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.CommentPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.CommentVO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.ICommentService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "CommentController", description = "评论相关接口")
@AllArgsConstructor
@Slf4j
public class CommentController {

    private final ICommentService commentService;

    @GetMapping("/{parentId}")
    @Operation(summary = "分页查询评论")
    public Result<PageVO<CommentVO>> page(
            @PathVariable("parentId") Long parentId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sortType", defaultValue = "DEFAULT") CommentSortType sortType) {
        CommentPageQueryDTO pageQueryDTO = CommentPageQueryDTO.builder()
                .userId(ThreadUtil.getUserId())
                .parentId(parentId)
                .page(page)
                .size(size)
                .sortType(sortType)
                .build();
        PageVO<CommentVO> pageVO = commentService.page(pageQueryDTO);
        return Result.success(pageVO);
    }

    @PostMapping("/{parentId}")
    @Operation(summary = "创建评论")
    public Result<Void> create(
            @PathVariable("parentId") Long parentId,
            @Valid @RequestBody CommentCreateDTO commentCreateDTO) {
        CommentDTO commentDTO = CommentDTO.builder()
                .userId(ThreadUtil.getUserId())
                .parentId(parentId)
                .parentType(commentCreateDTO.getParentType())
                .content(commentCreateDTO.getContent())
                .build();
        commentService.create(commentDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论")
    public Result<Void> delete(
            @PathVariable("id") Long id,
            @RequestParam("parentId") Long parentId,
            @RequestParam("parentType") ResourceType parentType) {
        CommentDTO commentDTO = CommentDTO.builder()
                .id(id)
                .userId(ThreadUtil.getUserId())
                .parentId(parentId)
                .parentType(parentType)
                .build();
        commentService.delete(commentDTO);
        return Result.success();
    }

}
