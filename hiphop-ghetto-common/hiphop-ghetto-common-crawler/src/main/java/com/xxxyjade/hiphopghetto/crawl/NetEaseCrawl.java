package com.xxxyjade.hiphopghetto.crawl;

import com.xxxyjade.hiphopghetto.crawl.config.WebMagicConfig;
import com.xxxyjade.hiphopghetto.crawl.pipeline.NetEaseAlbumPipeline;
import com.xxxyjade.hiphopghetto.crawl.processor.NetEaseAlbumProcessor;
import com.xxxyjade.hiphopghetto.crawl.processor.NetEaseSongProcessor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class NetEaseCrawl {

    private final NetEaseAlbumProcessor netEaseAlbumCrawlProcessor;
    private final NetEaseSongProcessor netEaseSongProcessor;
    private final NetEaseAlbumPipeline netEaseAlbumPipeline;

    public void crawlAlbums(List<Long> albumIds) {
        Spider spider = Spider.create(netEaseAlbumCrawlProcessor).addPipeline(netEaseAlbumPipeline);
        albumIds.forEach(id -> spider.addUrl(WebMagicConfig.ALBUM_URL + id));
        spider.thread(1) // 单线程避免请求过于频繁
                .run();
    }

    public void crawlSongs(List<Long> songIds, Long albumId, LocalDate releaseTime) {
        Spider spider = Spider.create(netEaseSongProcessor);
        songIds.forEach(id -> {
            Request request = new Request(WebMagicConfig.SONG_URL + id);
            request.putExtra("albumId", albumId);
            request.putExtra("releaseTime", releaseTime);
            spider.addRequest(request);
        });
        spider.thread(1) // 单线程避免请求过于频繁
                .run();
    }

}
