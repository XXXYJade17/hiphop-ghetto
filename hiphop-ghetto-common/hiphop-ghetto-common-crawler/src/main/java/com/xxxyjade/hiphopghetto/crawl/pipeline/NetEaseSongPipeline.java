package com.xxxyjade.hiphopghetto.crawl.pipeline;

import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.service.ISongService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
@AllArgsConstructor
public class NetEaseSongPipeline implements Pipeline {

    private final ISongService ISongService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Page page = resultItems.get("__page__");
        if (page.getUrl().regex("https?://music\\.163\\.com/song\\?id=\\d+").match())  {
            Song song = resultItems.get("song");
            ISongService.insertIgnore(song);
        }
    }
}
