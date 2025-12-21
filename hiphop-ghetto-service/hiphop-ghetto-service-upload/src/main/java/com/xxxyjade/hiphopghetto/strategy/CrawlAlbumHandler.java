package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.crawl.impl.CloudMusicCrawler;
import com.xxxyjade.hiphopghetto.enums.Strategy;
import com.xxxyjade.hiphopghetto.message.CrawlMessage;
import com.xxxyjade.hiphopghetto.pojo.dto.CrawAlbumDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.service.AlbumService;
import com.xxxyjade.hiphopghetto.service.SongService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CrawlAlbumHandler implements MessageHandler<CrawlMessage> {

    private final CloudMusicCrawler cloudMusicCrawler;
    private final AlbumService albumService;
    private final SongService songService;

    @Override
    public void handle(CrawlMessage message) {
        log.info("处理消息: {}", message);
        CrawAlbumDTO crawAlbumDTO = cloudMusicCrawler.crawlByAlbumId(message.getId());
        Album album = crawAlbumDTO.getAlbum();
        List<Song> songs = crawAlbumDTO.getSongs();
        albumService.saveAll(List.of(album));
        songService.saveAll(songs);
    }

    @Override
    public List<Strategy> supports() {
        return List.of(Strategy.CRAWL_ALBUM);
    }

}
