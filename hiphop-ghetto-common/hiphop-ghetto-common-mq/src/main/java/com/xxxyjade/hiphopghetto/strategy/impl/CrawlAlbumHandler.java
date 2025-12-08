package com.xxxyjade.hiphopghetto.strategy.impl;

import com.xxxyjade.hiphopghetto.crawl.impl.CloudMusicCrawler;
import com.xxxyjade.hiphopghetto.domain.Message;
import com.xxxyjade.hiphopghetto.enums.MessageType;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.repository.AlbumRepository;
import com.xxxyjade.hiphopghetto.repository.SongRepository;
import com.xxxyjade.hiphopghetto.strategy.MessageHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CrawlAlbumHandler implements MessageHandler<String> {

    private final CloudMusicCrawler cloudMusicCrawler;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    @Override
    public void handle(Message<String> message) {
        log.info("处理消息: {}", message);
        Album album = cloudMusicCrawler.crawlByAlbumId(message.getBody());
        albumRepository.saveAll(List.of(album));
        songRepository.saveAll(album.getSongs());
    }

    @Override
    public MessageType support() {
        return MessageType.CRAWL_ALBUM;
    }
}
