package com.xxxyjade.hiphopghetto.crawl.pipeline;

import com.xxxyjade.hiphopghetto.crawl.NetEaseCrawl;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.service.IAlbumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
@AllArgsConstructor
public class NetEaseAlbumPipeline implements Pipeline {

    private final IAlbumService IAlbumService;
    private final NetEaseCrawl netEaseCrawl;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Page page = resultItems.get("__page__");
        if (page.getUrl().regex("https?://music\\.163\\.com/album\\?id=\\d+").match()) {
            Album album = resultItems.get("album");
            IAlbumService.insertIgnore(album);
            List<Long> songIds = resultItems.get("songIds");
            netEaseCrawl.crawlSongs(songIds, album.getId(), album.getReleaseTime());
        }
    }
}
