package com.xxxyjade.hiphopghetto.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xxxyjade.hiphopghetto.constant.MessageQueueConstant;
import com.xxxyjade.hiphopghetto.crawl.impl.CloudMusicCrawler;
import com.xxxyjade.hiphopghetto.domain.Message;
import com.xxxyjade.hiphopghetto.enums.MessageType;
import com.xxxyjade.hiphopghetto.sender.impl.MessageSender;
import com.xxxyjade.hiphopghetto.service.IUploadService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class UploadService implements IUploadService {

    private final MessageSender messageSender;

    /**
     * 文件上传
     * @param file 文件
     * @return 文件路径
     */
    @SneakyThrows
    public String uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = UUID.randomUUID() + extension;
        OSS ossClient = new OSSClientBuilder().build(
                "oss-cn-beijing.aliyuncs.com",
                "LTAI5tDQebvmjuj7K4W38nd6",
                "K3S4pV829LEtLni9tdpfcOXPyh5TLv");

        try {
            // 创建PutObject请求。
            ossClient.putObject("xxxyjade-hiphop-home", objectName, new ByteArrayInputStream(file.getBytes()));
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return "https://" +
                "xxxyjade-hiphop-home" +
                "." +
                "oss-cn-beijing.aliyuncs.com" +
                "/" +
                objectName;
    }

    /**
     * 根据专辑爬取专辑详情
     * @param albumId 专辑ID
     */
    public void crawlAlbumId(String albumId) {
        Message<String> message = Message.<String>builder()
                .body(albumId)
                .type(MessageType.CRAWL_ALBUM)
                .queue(MessageQueueConstant.CRAWl_QUEUE)
                .build();
        messageSender.send(message);
    }

//    /**
//     * 根据歌手爬取专辑
//     * @param artistId 歌手ID
//     */
//    public void crawlByArtistId(Long artistId) {
//        List<AlbumDTO> albumDTOList = cloudMusicCrawler.crawlByArtistId(artistId);
//        albumDTOList.forEach(albumDTO -> {
//            if (albumDTO == null) {
//                return;
//            }
//            albumMapper.insertOrUpdate(albumDTO.getAlbum());
//            songMapper.insertOrUpdate(albumDTO.getSongs());
//        });
//    }
}
