package com.xxxyjade.hiphopghetto.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

    String uploadFile(MultipartFile file);

    void crawlAlbumId(String albumId);

//    void crawlByArtistId(Long artistId);

}
