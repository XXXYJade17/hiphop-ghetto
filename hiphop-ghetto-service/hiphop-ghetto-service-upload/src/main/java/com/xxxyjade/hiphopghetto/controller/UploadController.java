package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.result.Result;
import com.xxxyjade.hiphopghetto.service.impl.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@Tag(name = "UploadController", description = "文件上传接口")
@AllArgsConstructor
@Slf4j
public class UploadController {

    private final UploadService uploadService;

    @PostMapping(value = "/file")
    @Operation(summary = "文件上传")
    public Result<String> uploadFile(MultipartFile file){
        return Result.success(uploadService.uploadFile(file));
    }

    @GetMapping("/crawl/album/{id}")
    @Operation(summary = "根据专辑id爬取专辑")
    public Result<Void> crawlByAlbumId(@PathVariable("id") String albumId) {
        uploadService.crawlAlbumId(albumId);
        return Result.success();
    }

//    @GetMapping("/crawl/artist/{id}")
//    @Operation(summary = "根据歌手id爬取专辑")
//    public Result<Void> crawlAlbumsByArtistId(@PathVariable("id") Long artistId) {
//        uploadService.crawlByArtistId(artistId);
//        return Result.success();
//    }

}
